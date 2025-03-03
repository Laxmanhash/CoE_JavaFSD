import java.util.*;

public class RoomScheduler {
    private Map<String, MeetingRoom> roomMap;

    public RoomScheduler() {
        roomMap = new HashMap<>();
    }

    // Add a new meeting room
    public void addMeetingRoom(MeetingRoom room) {
        roomMap.put(room.getRoomId(), room);
        System.out.println("Room added: " + room.getRoomName() + ", ID: " + room.getRoomId());
    }

    // Book a room based on required features
    public String bookRoom(String roomId, EnumSet<RoomFeature> requiredFeatures) {
        MeetingRoom room = roomMap.get(roomId);

        if (room == null) {
            return "Room not found.";
        }

        // Check if the room meets all the required features
        if (room.getFeatures().containsAll(requiredFeatures)) {
            return "Room " + roomId + " booked successfully.";
        } else {
            return "Room " + roomId + " does not meet the required features.";
        }
    }

    // List available rooms based on required features
    public List<String> listAvailableRooms(EnumSet<RoomFeature> requiredFeatures) {
        List<String> availableRooms = new ArrayList<>();

        for (MeetingRoom room : roomMap.values()) {
            if (room.getFeatures().containsAll(requiredFeatures)) {
                availableRooms.add(room.getRoomName());
            }
        }

        return availableRooms;
    }
}
