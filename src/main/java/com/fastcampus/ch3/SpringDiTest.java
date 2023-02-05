//package com.fastcampus.ch3;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Arrays;
//
//@Component
//class Car {
//    @Value("red")
//    String color;
//    @Value("100")
//    int oil;
////    @Autowired
////    @Qualifier("turboEngine")
//    @Resource(name = "superEngine")
//    Engine engine;
//    @Autowired
//    Door[] doors;
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public void setOil(int oil) {
//        this.oil = oil;
//    }
//
//    public void setEngine(Engine engine) {
//        this.engine = engine;
//    }
//
//    public void setDoors(Door[] doors) {
//        this.doors = doors;
//    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "color='" + color + '\'' +
//                ", oil=" + oil +
//                ", engine=" + engine +
//                ", doors=" + Arrays.toString(doors) +
//                '}';
//    }
//}
//@Component
//class Engine {}
//@Component class SuperEngine extends Engine {}
//@Component class TurboEngine extends Engine {}
//@Component
//class Door {}
//
//public class SpringDiTest {
//    public static void main(String[] args) {
//        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
//        Car car = (Car) ac.getBean("car");  // byName
//        Car car2 = ac.getBean("car", Car.class);
////        Engine engine = (Engine) ac.getBean("engine"); // byName
//        Engine engine = (Engine) ac.getBean("superEngine");
//        Door door = (Door) ac.getBean("door"); // byName
//
////        car.setColor("red");
////        car.setOil(100);
////        car.setEngine(engine);
////        car.setDoors(new Door[]{ac.getBean("door", Door.class), ac.getBean("door", Door.class)});
//        System.out.println("car = " + car);
//        System.out.println("engine = " + engine);
//        System.out.println(ac.getBeanDefinitionCount());
//    }
//}
