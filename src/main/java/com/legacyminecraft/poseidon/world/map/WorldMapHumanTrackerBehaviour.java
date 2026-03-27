package com.legacyminecraft.poseidon.world.map;

import com.legacyminecraft.compat.bukkit.MapCursor;
import com.legacyminecraft.compat.bukkit.WorldMapRenderBridgeBehaviour;

import java.util.List;

public final class WorldMapHumanTrackerBehaviour {
    private static final WorldMapHumanTrackerBehaviour INSTANCE = new WorldMapHumanTrackerBehaviour();
    private static final WorldMapRenderBridgeBehaviour WORLD_MAP_RENDER_BRIDGE = WorldMapRenderBridgeBehaviour.getInstance();

    private WorldMapHumanTrackerBehaviour() {
    }

    public static WorldMapHumanTrackerBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeBounds(int[] minByColumn, int[] maxByColumn) {
        for (int column = 0; column < minByColumn.length; ++column) {
            minByColumn[column] = 0;
            maxByColumn[column] = 127;
        }
    }

    public UpdateState createUpdate(Object worldMap, Object trackee, Object itemstack, int[] minByColumn, int[] maxByColumn,
                                    int updateCursor, int markerCooldown, byte[] lastMarkerPacket) {
        WorldMapRenderBridgeBehaviour.RenderSnapshot render = WORLD_MAP_RENDER_BRIDGE.render(worldMap, trackee);

        if (--markerCooldown < 0) {
            markerCooldown = 4;
            byte[] markerPacket = buildMarkerPacket(render);

            boolean unchanged = true;
            if (lastMarkerPacket != null && lastMarkerPacket.length == markerPacket.length) {
                for (int markerIndex = 0; markerIndex < markerPacket.length; ++markerIndex) {
                    if (markerPacket[markerIndex] != lastMarkerPacket[markerIndex]) {
                        unchanged = false;
                        break;
                    }
                }
            } else {
                unchanged = false;
            }

            if (!unchanged) {
                return new UpdateState(updateCursor, markerCooldown, markerPacket, markerPacket);
            }
        }

        for (int k = 0; k < 10; ++k) {
            int column = updateCursor * 11 % 128;
            ++updateCursor;
            if (minByColumn[column] >= 0) {
                int length = maxByColumn[column] - minByColumn[column] + 1;
                int rowStart = minByColumn[column];
                byte[] dataPacket = new byte[length + 3];

                dataPacket[0] = 0;
                dataPacket[1] = (byte) column;
                dataPacket[2] = (byte) rowStart;

                for (int offset = 0; offset < dataPacket.length - 3; ++offset) {
                    dataPacket[offset + 3] = render.getBuffer()[(offset + rowStart) * 128 + column];
                }

                maxByColumn[column] = -1;
                minByColumn[column] = -1;
                return new UpdateState(updateCursor, markerCooldown, lastMarkerPacket, dataPacket);
            }
        }

        return new UpdateState(updateCursor, markerCooldown, lastMarkerPacket, null);
    }

    private byte[] buildMarkerPacket(WorldMapRenderBridgeBehaviour.RenderSnapshot render) {
        List<MapCursor> cursors = render.getCursors();
        byte[] packet = new byte[cursors.size() * 3 + 1];
        packet[0] = 1;

        for (int cursorIndex = 0; cursorIndex < cursors.size(); ++cursorIndex) {
            MapCursor cursor = cursors.get(cursorIndex);
            if (!cursor.isVisible()) {
                continue;
            }

            byte value = (byte) (((cursor.getRawType() == 0 || cursor.getDirection() < 8 ? cursor.getDirection() : cursor.getDirection() - 1) & 15) * 16);
            packet[cursorIndex * 3 + 1] = (byte) (value | (cursor.getRawType() != 0 && value < 0 ? 16 - cursor.getRawType() : cursor.getRawType()));
            packet[cursorIndex * 3 + 2] = (byte) cursor.getX();
            packet[cursorIndex * 3 + 3] = (byte) cursor.getY();
        }

        return packet;
    }

    public static final class UpdateState {
        public final int updateCursor;
        public final int markerCooldown;
        public final byte[] lastMarkerPacket;
        public final byte[] payload;

        UpdateState(int updateCursor, int markerCooldown, byte[] lastMarkerPacket, byte[] payload) {
            this.updateCursor = updateCursor;
            this.markerCooldown = markerCooldown;
            this.lastMarkerPacket = lastMarkerPacket;
            this.payload = payload;
        }
    }
}
