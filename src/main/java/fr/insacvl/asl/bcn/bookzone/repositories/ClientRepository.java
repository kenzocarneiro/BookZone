package fr.insacvl.asl.bcn.bookzone.repositories;

import fr.insacvl.asl.bcn.bookzone.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByLogin(String login);
}
