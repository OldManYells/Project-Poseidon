package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.EntityTrackingAttachmentSystem;
import com.legacyminecraft.poseidon.entity.EntityTrackingDispatchSystem;
import com.legacyminecraft.poseidon.entity.EntityTrackingFrameBehaviour;
import com.legacyminecraft.poseidon.entity.EntityTrackingMovementProcessor;
import com.legacyminecraft.poseidon.entity.EntityTrackingScanSystem;
import com.legacyminecraft.poseidon.entity.EntityTrackingState;
import com.legacyminecraft.poseidon.entity.EntityTrackingTickPolicy;
import com.legacyminecraft.poseidon.entity.EntityVelocityChangeProcessor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityTrackerEntry {

    public Entity tracker;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public double i;
    public double j;
    public double k;
    public int l = 0;
    private double o;
    private double p;
    private double q;
    private boolean r = false;
    private boolean isMoving;
    private int t = 0;
    public boolean m = false;
    public Set trackedPlayers = new HashSet();
    private final EntityTrackingDispatchSystem trackingDispatchSystem = EntityTrackingDispatchSystem.getInstance();
    private final EntityTrackingMovementProcessor trackingMovementProcessor = EntityTrackingMovementProcessor.getInstance();
    private final EntityTrackingScanSystem trackingScanSystem = EntityTrackingScanSystem.getInstance();
    private final EntityTrackingTickPolicy trackingTickPolicy = EntityTrackingTickPolicy.getInstance();
    private final EntityVelocityChangeProcessor velocityChangeProcessor = EntityVelocityChangeProcessor.getInstance();
    private final EntityTrackingFrameBehaviour trackingFrameBehaviour = EntityTrackingFrameBehaviour.getInstance();

    public EntityTrackerEntry(Entity entity, int i, int j, boolean flag) {
        this.tracker = entity;
        this.b = i;
        this.c = j;
        this.isMoving = flag;
        this.d = MathHelper.floor(entity.locX * 32.0D);
        this.e = MathHelper.floor(entity.locY * 32.0D);
        this.f = MathHelper.floor(entity.locZ * 32.0D);
        this.g = MathHelper.d(entity.yaw * 256.0F / 360.0F);
        this.h = MathHelper.d(entity.pitch * 256.0F / 360.0F);
    }

    public boolean equals(Object object) {
        return object instanceof EntityTrackerEntry ? ((EntityTrackerEntry) object).tracker.id == this.tracker.id : false;
    }

    public int hashCode() {
        return this.tracker.id;
    }

    public void track(List list) {
        this.m = false;
        EntityTrackingFrameBehaviour.FrameDecision frameDecision = trackingFrameBehaviour.decideFrame(
                this.tracker,
                this.trackingTickPolicy,
                this.r,
                this.o,
                this.p,
                this.q,
                this.l,
                this.c
        );
        if (frameDecision.shouldRescanPlayers()) {
            EntityTrackingFrameBehaviour.RescanAnchor rescanAnchor = trackingFrameBehaviour.captureRescanAnchor(this.tracker);
            this.o = rescanAnchor.getX();
            this.p = rescanAnchor.getY();
            this.q = rescanAnchor.getZ();
            this.r = true;
            this.m = true;
            this.scanPlayers(list);
        }

        this.l = frameDecision.getNextTickCounter();
        if (frameDecision.shouldProcessTrackingFrame()) {
            EntityTrackingState trackingState = trackingFrameBehaviour.createTrackingState(
                    this.d,
                    this.e,
                    this.f,
                    this.g,
                    this.h,
                    this.t + 1,
                    this.i,
                    this.j,
                    this.k
            );

            trackingMovementProcessor.processTrackingFrame(
                    this.tracker,
                    trackingState,
                    this.isMoving,
                    this.trackedPlayers,
                    this.trackingDispatchSystem,
                    new Runnable() {
                        @Override
                        public void run() {
                            scanPlayers(new java.util.ArrayList(trackedPlayers));
                        }
                    }
            );

            this.d = trackingState.getEncodedX();
            this.e = trackingState.getEncodedY();
            this.f = trackingState.getEncodedZ();
            this.g = trackingState.getEncodedYaw();
            this.h = trackingState.getEncodedPitch();
            this.t = trackingState.getTeleportCounter();
            this.i = trackingState.getMotionX();
            this.j = trackingState.getMotionY();
            this.k = trackingState.getMotionZ();
        }

        trackingFrameBehaviour.processVelocityChangeIfNeeded(
                this.tracker.velocityChanged,
                this.tracker,
                this.trackedPlayers,
                this.velocityChangeProcessor,
                this.trackingDispatchSystem
        );
    }

    public void a(Packet packet) {
        trackingDispatchSystem.sendToTrackedPlayers(this.trackedPlayers, packet);
    }

    public void b(Packet packet) {
        trackingDispatchSystem.sendToTrackedPlayersAndSelf(this.trackedPlayers, this.tracker, packet);
    }

    public void a() {
        trackingDispatchSystem.queueDestroyForTrackedPlayers(this.trackedPlayers, this.tracker.id);
    }

    public void a(EntityPlayer entityplayer) {
        trackingDispatchSystem.removeTrackedPlayer(this.trackedPlayers, entityplayer, this.tracker.id);
    }

    public void b(EntityPlayer entityplayer) {
        applyMotionSnapshot(
                trackingScanSystem.scanPlayer(
                        this.tracker,
                        entityplayer,
                        this.trackedPlayers,
                        this.b,
                        this.d,
                        this.f,
                        this.isMoving
                )
        );
    }

    public void scanPlayers(List list) {
        applyMotionSnapshot(
                trackingScanSystem.scanPlayers(
                        this.tracker,
                        list,
                        this.trackedPlayers,
                        this.b,
                        this.d,
                        this.f,
                        this.isMoving
                )
        );
    }

    public void c(EntityPlayer entityplayer) {
        trackingDispatchSystem.removeTrackedPlayer(this.trackedPlayers, entityplayer, this.tracker.id);
    }

    private void applyMotionSnapshot(EntityTrackingAttachmentSystem.MotionSnapshot motionSnapshot) {
        if (motionSnapshot == null) {
            return;
        }

        this.i = motionSnapshot.getMotionX();
        this.j = motionSnapshot.getMotionY();
        this.k = motionSnapshot.getMotionZ();
    }
}
