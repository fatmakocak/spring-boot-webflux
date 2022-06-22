package com.example.springbootwebflux;

import com.example.springbootwebflux.entity.Employee;
import com.example.springbootwebflux.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class SpringBootWebfluxApplication {

    @Autowired
    private EmployeeRepository employeeRepository;


    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxApplication.class, args);

    }

    @EventListener(ApplicationStartedEvent.class)
    public void appStart() {
        if (employeeRepository.count().block() == 0) { ////varsa veritabanından gelen kaydı bekle

            IntStream.range(0, 100)
                    .mapToObj(this::generate)
                    .map(employeeRepository::save)
                    .collect(Collectors.toList())
                    // .forEach(item -> item.subscribe(System.out::println, System.out::println));//onSuccess olduğunda yani başarılı bir şekilde kaydedildiğinde  1. paramerte,
                    //veri tabanına doğru kaydolmuş case i, ikinci fonksiyonum ise hata almışsam şu işletilsin
                    //1. kaydolmuşsa ekrana yazıyor. 2. hata almışsa da ekrana yazıyor.
                    /*   .forEach(item->item.subscribe(
                               (t)-> System.out.println(t),
                               (err)-> System.out.println(err)));
       */
                    .forEach(item -> item.subscribe(System.out::println));//kısa hali error kısmı ile ilgilenmiyorum sadece veri tabnına yazılan nesneyi direk console a yazsın
        }


    } //her çalıştırdığımda 100 kayıt eklemesini engellemek için if ekledim

    private Employee generate(int i) {
        return Employee.builder()
                .name("name" + i)
                .lastname("lastname" + i)
                .birthDate(LocalDate.now())
                .build();

    }
}
