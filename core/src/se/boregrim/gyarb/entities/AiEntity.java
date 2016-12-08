package se.boregrim.gyarb.entities;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.pfa.PathFinder;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.ReachOrientation;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import se.boregrim.gyarb.screens.GameScreen;
import se.boregrim.gyarb.utils.Constants;
import se.boregrim.gyarb.utils.SteeringUtils;

/**
 * Created by robin.boregrim on 2016-12-01.
 */
public class AiEntity extends Actor  implements Steerable<Vector2>{
    boolean tagged;
    float boundingRadius;
    float maxLinearSpeed, maxLinearAcceleration;
    float maxAngularSpeed, maxAngularAcceleration;

    SteeringBehavior<Vector2> behavior;
    SteeringAcceleration<Vector2> steeringOutput;
    public AiEntity(GameScreen gs, float boundingRadius) {
        super(gs);



        this.boundingRadius = boundingRadius;

        maxLinearSpeed = 500;
        maxLinearAcceleration = 5000;
        maxAngularSpeed = 30;
        maxAngularAcceleration = 5;

        tagged = false;

        steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        //body.setUserData(this);

        
    }

    @Override
    public void update(float delta){
    super.update(delta);
        if (behavior != null){
            behavior.calculateSteering(steeringOutput);
            applySteering(delta);
        }
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
                //body.applyTorque();
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
