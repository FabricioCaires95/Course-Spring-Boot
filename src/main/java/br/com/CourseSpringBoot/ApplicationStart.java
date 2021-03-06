package br.com.CourseSpringBoot;

import br.com.CourseSpringBoot.domain.*;
import br.com.CourseSpringBoot.enums.ClientType;
import br.com.CourseSpringBoot.enums.StatePayment;
import br.com.CourseSpringBoot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author fabricio
 */

@SpringBootApplication
public class ApplicationStart implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public static void main(String[] args){

        SpringApplication.run(ApplicationStart.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        Category c1 = new Category(null, "Eletronics");
        Category c2 = new Category(null, "Office");

        Product p1 = new Product(null, "Computer", 2000.00);
        Product p2 = new Product(null, "Printer", 800.00);
        Product p3 = new Product(null, "Mouse", 80.00);

        c1.getProducts().addAll(Arrays.asList(p1,p2,p3));
        c2.getProducts().addAll(Arrays.asList(p2));

        p1.getCategories().addAll(Arrays.asList(c1));
        p2.getCategories().addAll(Arrays.asList(c1,c2));
        p3.getCategories().addAll(Arrays.asList(c1));

        State s1 = new State(null, "California");
        State s2 = new State(null, "Florida");

        City ci1 = new City(null, "Los Angeles", s1);
        City ci2 = new City(null, "Miami", s2);
        City ci3 = new City(null, "Orlando", s2);
        City ci4 = new City(null, "San Francisco", s1);

        Client t1 = new Client(null,"Fabricio Santos", "fabricio@gmail.com", "123456789", ClientType.PHYISICALPERSON) ;
        t1.getPhones().addAll(Arrays.asList("36525877", "987548728"));


        Address ad1 = new Address(null,"st times square", "52", "301408", t1, ci1);
        Address ad2 = new Address(null,"21t street ", "51", "308170", t1, ci4);






        t1.getAddresses().addAll(Arrays.asList(ad1,ad2));

        s1.getCities().addAll(Arrays.asList(ci1,ci4));
        s2.getCities().addAll(Arrays.asList(ci2,ci3));

        categoryRepository.saveAll(Arrays.asList(c1,c2));
        productRepository.saveAll(Arrays.asList(p1,p2,p3));

        stateRepository.saveAll(Arrays.asList(s1,s2));
        cityRepository.saveAll(Arrays.asList(ci1,ci2,ci3,ci4));

        clientRepository.saveAll(Arrays.asList(t1));
        addressRepository.saveAll(Arrays.asList(ad1,ad2));

        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Order o1 = new Order(null, sd.parse("30/09/2018 11:52"), t1,ad2);

        Order o2 = new Order(null, sd.parse("29/10/2018 15:52"), t1,ad1);

        t1.getOrders().addAll(Arrays.asList(o1,o2));

        Payment pg1 = new CreditCard(null, StatePayment.PAID, o1, 6);
        o1.setPayment(pg1);

        Payment pg2 = new PayPal(null, StatePayment.CANCELED, o2,"fabricio@gmail.com", "1111");
        o2.setPayment(pg2);

        orderRepository.saveAll(Arrays.asList(o1, o2));

        paymentRepository.saveAll(Arrays.asList(pg1, pg2));

        OrderItem oi1 = new OrderItem(o1,p1,0.10, 50, 1.500);

        OrderItem oi2 = new OrderItem(o2,p2,0.00, 1, 500.00);

        OrderItem oi3 = new OrderItem(o2,p3,0.00, 1, 300.00);

        o1.getOrderItems().addAll(Arrays.asList(oi1,oi2));

        p1.getOrderItems().addAll(Arrays.asList(oi3));

        orderItemRepository.saveAll(Arrays.asList(oi1,oi2,oi3));
    }
}
