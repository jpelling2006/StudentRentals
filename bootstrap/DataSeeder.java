package bootstrap;

import java.time.LocalDate;

import booking.Booking;
import booking.BookingStatus;
import properties.Property;
import properties.PropertyQueryService;
import properties.PropertyType;
import review.Review;
import room.Room;
import room.RoomType;
import user.AdministratorUser;
import user.HomeownerUser;
import user.LoggedOutManager;
import user.StudentUser;

// for the sole purpose of testing
// creates test data so it doesnt need to be typed in each time
public final class DataSeeder {
    public static void seed() {
        try {
            StudentUser student = new StudentUser(
                "student",
                "student@uni.ac.uk",
                "0123456789",
                "password123",
                "Uni Name",
                "123456"
            );
            LoggedOutManager.addUser(student);

            HomeownerUser homeowner = new user.HomeownerUser(
                "ian", 
                "ian@icantwork.com", 
                "0987654321", 
                "password456"
            );
            LoggedOutManager.addUser(homeowner);

            AdministratorUser admin = new user.AdministratorUser(
                "admin", 
                "admin@sr.com", 
                "0192837465", 
                "adminPass1"
            );
            LoggedOutManager.addUser(admin);

            Property property1 = new Property(
                homeowner, 
                "Cardiff", 
                "Senghennydd Road, Cathays, Cardiff CF24 4AG", 
                "A lovely little townhouse. Don't mind the mould!", 
                PropertyType.HOUSE, 
                5, 
                2
            );
            PropertyQueryService.indexProperty(property1);

            Property property2 = new Property(
                homeowner, 
                "High Wycombe", 
                "Amersham Hill, High Wycombe, Buckinghamshire, HP13 6NN", 
                "Right on the outskirts of High Wycombe. You are so close to escaping!",
                PropertyType.FLAT, 
                3, 
                1
            );
            PropertyQueryService.indexProperty(property2);

            Room room1 = new Room(
                property1,
                RoomType.DOUBLE, 
                150.0, 
                true, 
                "First floor rear", 
                "Desk, wardrobe, a little rat hole in the corner for all your new friends!",
                LocalDate.parse("2026-07-01"),
                LocalDate.parse("2027-06-30")
            );

            Room room2 = new Room(property2,
                RoomType.SINGLE, 
                125.5, 
                true, 
                "Ground floor front", 
                "Desk, wardrobe, and various slugs!",
                LocalDate.parse("2025-07-01"),
                LocalDate.parse("2026-06-30")
            );

            new Booking(
                BookingStatus.PENDING,
                room1,
                student.getUsername(),
                LocalDate.parse("2026-08-01"),
                LocalDate.parse("2027-06-30")
            );

            new Booking(
                BookingStatus.ENDED,
                room2,
                student.getUsername(),
                LocalDate.parse("2025-08-01"),
                LocalDate.parse("2025-09-30")
            );

            new Review(
                property2,
                student.getUsername(), 
                2, 
                "Place smells!", 
                "Property had an incredibly horrible mouldy smell, and the landlord did nothing!"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
