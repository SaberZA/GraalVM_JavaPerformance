package io.stevenv.graalvm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class GraalvmApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraalvmApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(DatabaseClient dbc,
										ReservationRespository reservationRespository) {
		return args -> {
			Flux<Reservation> reservationFlux = reservationRespository.saveAll(
					Flux.just(new Reservation(null, "Andy"),
							new Reservation(null, "Timmy"),
							new Reservation(null, "Josh")));
			dbc.execute("create table reservation ( id serial primary key, name varchar(255) not null ) ;")
					.fetch()
					.rowsUpdated()
					.thenMany(reservationFlux)
					.thenMany(reservationRespository.findAll())
					.subscribe(System.out::println);

		};
	}
}

interface ReservationRespository extends ReactiveCrudRepository<Reservation, Integer> {

}

class Reservation {

	public Reservation(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Reservation{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}

	@Id
	public Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}