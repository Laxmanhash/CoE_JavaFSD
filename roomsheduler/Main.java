import java.util.EnumSet;

public class Main {
    public static void main(String[] args) {
        RoomScheduler scheduler = new RoomScheduler();

        // Add meeting rooms
        scheduler.addMeetingRoom(new MeetingRoom("001", "Boardroom", 20, EnumSet.of(RoomFeature.PROJECTOR, RoomFeature.CONFERENCE_PHONE, RoomFeature.AIR_CONDITIONING)));
        scheduler.addMeetingRoom(new MeetingRoom("002", "Strategy Room", 10, EnumSet.of(RoomFeature.WHITEBOARD, RoomFeature.AIR_CONDITIONING)));

        // Book a room
        System.out.println(scheduler.bookRoom("001", EnumSet.of(RoomFeature.PROJECTOR, RoomFeature.CONFERENCE_PHONE)));

        // List available rooms with AIR_CONDITIONING
        System.out.println("Available rooms with AIR_CONDITIONING: " + scheduler.listAvailableRooms(EnumSet.of(RoomFeature.AIR_CONDITIONING)));
    }
}
