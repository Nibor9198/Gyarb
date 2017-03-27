package se.boregrim.gyarb.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.PathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.ReachOrientation;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import se.boregrim.gyarb.managers.MapManager;
import se.boregrim.gyarb.pathfinding.HeuristicImp;
import se.boregrim.gyarb.pathfinding.Node;
import se.boregrim.gyarb.pathfinding.Path;
import se.boregrim.gyarb.pathfinding.PathSteering;
import se.boregrim.gyarb.screens.GameScreen;
import se.boregrim.gyarb.utils.Constants;
import se.boregrim.gyarb.utils.SteeringUtils;

/**
 * Created by robin.boregrim on 2016-12-01.
 */
public class AiEntity extends Actor implements Steerable<Vector2>{
    boolean tagged;
    Location<Vector2> target;
    float boundingRadius;
    float maxLinearSpeed, maxLinearAcceleration;
    float maxAngularSpeed, maxAngularAcceleration;

    SteeringBehavior<Vector2> behavior;
    SteeringAcceleration<Vector2> steeringOutput;


    Arrive<Vector2> arrive;

    //Pathfinding
    DefaultStateMachine stateMachine;
    IndexedAStarPathFinder pathFinder;
    Path outpath;

    @Override
    public void die() {
        super.die();
        outpath.clear();
    }

    public AiEntity(GameScreen gs, float boundingRadius, Location<Vector2> target) {
        super(gs);


        this.target = target;

        this.boundingRadius = boundingRadius;

        maxLinearSpeed = 500;
        maxLinearAcceleration = 5000;
        maxAngularSpeed = 30;
        maxAngularAcceleration = 5;

        tagged = false;

        steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());




    }
    public void defaultSteering(){
        pathfindInit();

        //Arrive<Vector2> arrive = new Arrive<Vector2>(this, target)
        //        .setTimeToTarget(0.1f)
        //        .setArrivalTolerance(2f)
        //        .setDecelerationRadius(15);


        PathSteering pathArrive = new PathSteering(this, target);
        pathArrive
                .setTimeToTarget(0.001f)
                .setArrivalTolerance(0f)
                .setDecelerationRadius(15);
        //Seek<Vector2> seek = new Seek<Vector2>(this ,player);



        setBehavior(pathArrive);

    }

    private void pathfindInit(){

        //stateMachine = new DefaultStateMachine();
        pathFinder = gs.getPathFinder();

        pathfind();

    }

    public Path getOutpath() {
        return  pathfind();
    }

    private Path pathfind(){
        int startX;
        startX = (int) getPosition().x;
        int startY = (int) getPosition().y;
        int endX;
        endX = (int) target.getPosition().x;
        int endY = (int) target.getPosition().y;

        if(startX >= 0 && startY >= 0 && endX >= 0 && endY >= 0) {
            Node startNode = MapManager.graph.getNodeByPos(startX, startY);
            Node endNode = MapManager.graph.getNodeByPos(endX, endY);
            outpath = new Path();
            pathFinder.searchNodePath(startNode, endNode, gs.getHeuristic() , outpath);
            //System.out.println(outpath.getCount());
            return outpath;
        }else{
            return null;
        }
    }


    @Override
    public void update(float delta){
    super.update(delta);
        if (behavior != null && steeringOutput != null){
            behavior.calculateSteering(steeringOutput);
            applySteering(delta);
        }
        //if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
        //    pathfind();
        //}

    }

    private void applySteering(float delta){
        boolean anyAccelerations = false;

        if (!steeringOutput.linear.isZero()){
            Vector2 force = steeringOutput.linear.scl(delta);
            body.applyForceToCenter(force,true);

            body.applyTorque(steeringOutput.angular *delta, true);
            anyAccelerations = true;
        }
        if(steeringOutput.angular != 0){
            body.applyTorque(steeringOutput.angular *delta, true);
            anyAccelerations = true;
        }else{
            Vector2 linearVelocity = getLinearVelocity();
            if(!linearVelocity.isZero()){
                float newOrientation = vectorToAngle(linearVelocity);
                body.setAngularVelocity((newOrientation - getAngularVelocity()) * delta);
                body.setTransform(body.getPosition(), (float) (newOrientation+Math.PI/2));
            }
        }

        if(anyAccelerations){
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = getLinearVelocity().len2();
            if(currentSpeedSquare > maxLinearSpeed * maxLinearSpeed){
                body.setLinearVelocity(velocity.scl(maxLinearSpeed/(float) (Math.sqrt(currentSpeedSquare))));
            }
        }
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        body.setTransform(body.getPosition(),orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector,angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    public Body getBody() {
        return body;
    }

    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        this.behavior = behavior;
    }

    public SteeringAcceleration<Vector2> getSteeringOutput() {
        return steeringOutput;
    }


}
