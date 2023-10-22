package enseirb.concurrence.restful;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Scanner;

public class RestfulClient {

    public static void main(String[] args) {

        // Creation of the Spring context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebClientConfig.class);

        // Retrieval of the TruckRepositoryClient bean from the context
        TruckRepositoryClient truckRepositoryClient = context.getBean(TruckRepositoryClient.class);

        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Type the id of the truck");
            long id = in.nextLong();

            Truck truck = truckRepositoryClient.getById(id);

            // Result
            if (truck != null) {
                System.out.println("Truck Details:");
                System.out.println("License Plate: " + truck.getLicencePlate());
                System.out.println("First release date: " + truck.getFirstReleaseDate());
                System.out.println("Unloaded weight: " + truck.getUnloadedWeight());
            } else {
                System.out.println("Truck not found.");
            }
        } catch (WebClientResponseException.NotFound e) {
            System.out.println("404 Error, the id isn't valid!");
        }

        // Closing the Spring context
        context.close();
    }
}
