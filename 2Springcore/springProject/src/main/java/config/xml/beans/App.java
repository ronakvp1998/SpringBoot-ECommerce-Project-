package config.xml.beans;

import another.world.Repo;
import another.world.UserRepo;
import org.example.withoutboot.beans.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {

    public static void main(String[] args) {
        System.out.println("XML MAIN Started");
        ApplicationContext context = new ClassPathXmlApplicationContext("config/xml/beans/config.xml");
        Student student1 = context.getBean("student1", Student.class);
        student1.sayHello();
        System.out.println(student1);
        Address address = student1.getAddress();
        System.out.println(address);

        Map<String,String> map = student1.getMap();
        map.forEach((key,value) -> System.out.println(key + "->" + value));

        System.out.println(map.getClass().getName());
//        java.util.LinkedHashMap

        Repo r = context.getBean("repo",Repo.class);
        r.getUser();

        UserRepo bean1 = context.getBean(UserRepo.class);
        bean1.searchUser();

        OrderService os = context.getBean(OrderService.class);
        os.createOrder();
    }
}
