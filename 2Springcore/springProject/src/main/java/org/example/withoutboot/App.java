package org.example.withoutboot;

import another.world.Repo;
import another.world.RepoConfig;
import org.example.withoutboot.beans.CartService;
import org.example.withoutboot.beans.OrderService;
import org.example.withoutboot.beans.UserService;
import org.example.withoutboot.config.AppConfig;
import org.example.withoutboot.web.AuthController;
import org.example.withoutboot.web.HomeController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, RepoConfig.class);
        CartService cs =  context.getBean("cartService1",CartService.class);
        cs.createCart();

        UserService us= context.getBean(UserService.class);
        us.saveUser();

        OrderService os =  context.getBean(OrderService.class);
        os.createOrder();

        HomeController ho = context.getBean(HomeController.class);
        ho.homePage();

        AuthController au =  context.getBean(AuthController.class);
                au.login();

                Repo repo = context.getBean("repo",Repo.class);
                repo.getUser();
    }
}
