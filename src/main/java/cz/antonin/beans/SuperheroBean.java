package cz.antonin.beans;

import cz.antonin.models.Publisher;
import cz.antonin.models.Superhero;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class SuperheroBean {
    public List<Publisher> getPublishers() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3309/superhero?user=root&password=heslo");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM publisher")
        ) {
            ArrayList<Publisher> publishers = new ArrayList<>();

            while (resultSet.next()) {
                // put N/A if name is empty
                String name = resultSet.getString("publisher_name");
                if (name == null || name.isEmpty()) name = "N/A";

                publishers.add(new Publisher(resultSet.getInt("id"), name));
            }

            return publishers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPublisherName(int id) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3309/superhero?user=root&password=heslo");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT publisher_name FROM publisher WHERE id = " + id)
        ) {
            if (resultSet.next()) {
                return resultSet.getString("publisher_name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "N/A";
    }

    public int getHeroCount(int id) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3309/superhero?user=root&password=heslo");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT COUNT(publisher_id) AS count FROM superhero WHERE publisher_id = " + id)
        ) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public List<Superhero> getSuperheroes() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3309/superhero?user=root&password=heslo");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("" +
                        "SELECT * FROM superhero hero" +
                        " JOIN publisher ON hero.publisher_id = publisher.id" +
                        " JOIN gender on hero.gender_id = gender.id" +
                        " JOIN race on hero.race_id = race.id" +
                        " JOIN alignment on hero.alignment_id = alignment.id")
        ) {
            ArrayList<Superhero> heroes = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("hero.id");
                int publisherId = resultSet.getInt("hero.publisher_id");

                // put N/A if name is empty
                String pseudonym = resultSet.getString("hero.superhero_name");
                if (pseudonym == null || pseudonym.isEmpty()) pseudonym = "N/A";

                String fullName = resultSet.getString("hero.full_name");
                if (fullName == null || fullName.isEmpty()) fullName = "N/A";

                String gender = resultSet.getString("gender.gender");

                String race = resultSet.getString("race.race");

                String alignment = resultSet.getString("alignment.alignment");

                heroes.add(new Superhero(id, publisherId, pseudonym, fullName, gender, race, alignment));
            }

            return heroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
