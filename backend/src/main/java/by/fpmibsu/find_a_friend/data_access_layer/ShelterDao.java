package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShelterDao implements ShelterDaoInterface {
    private static final String SQL_SELECT_ALL_SHELTERS = """
            SELECT shelter_id, name, place.place_id, place.country, place.region, place.city, place.district
            FROM shelter
                LEFT JOIN place ON shelter.place_id=place.place_id""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT shelter_id, name, place.place_id, place.country, place.region, place.city, place.district
            FROM shelter
                LEFT JOIN place ON shelter.place_id=place.place_id
                WHERE shelter_id=?""";
    private static final String SQL_INSERT_SHELTER = """
            INSERT INTO shelter VALUES(?,?)""";
    private static final String SQL_DELETE_SHELTER = """
            DELETE
            FROM shelter
            WHERE shelter_id=?""";

    private static final String SQL_UPDATE_SHELTER = """
            UPDATE shelter
            SET place_id=?,
                name=?
            WHERE shelter_id=?
            """;

    private static final String SQL_DELETE_BY_PLACE_SHELTER = """
            DELETE
            FROM shelter
            WHERE place_id=?
            """;

    private final StatementBuilder statementBuilder;
    private final Connection connection;

    public ShelterDao(Connection connection) {
        this.connection = connection;
        this.statementBuilder = new StatementBuilder(connection);
    }

    @Override
    public List<Shelter> getAll() throws DaoException {
        List<Shelter> shelters = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_SHELTERS);
            UserShelterDao userShelterDao = new UserShelterDao(connection);
            while (resultSet.next()) {
                Shelter shelter = EntityProducer.makeShelter(resultSet);
                List<Integer> user_ides = userShelterDao.getUsersId(shelter.getId());
                List<User> administrators = new ArrayList<>();
                List<AnimalAdvert> animalAdverts = new ArrayList<>();
                UserDao userDao = new UserDao(connection);
                AnimalAdvertDao animalAdvertDao = new AnimalAdvertDao(connection);
                for (int i = 0; i < user_ides.size(); i++) {
                    administrators.add(userDao.getEntityById(user_ides.get(i)));
                    animalAdverts.addAll(animalAdvertDao.getUsersAdverts(user_ides.get(i)));
                }
                shelter.setAdministrators(administrators);
                shelter.setAnimalAdverts(animalAdverts);
                shelters.add(shelter);
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
        return shelters;
    }

    @Override
    public Shelter getEntityById(Integer id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder
                    .prepareStatement(SQL_SELECT_BY_ID, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            UserShelterDao userShelterDao = new UserShelterDao(connection);
            Shelter shelter = EntityProducer.makeShelter(resultSet);
            List<Integer> user_ides = userShelterDao.getUsersId(shelter.getId());
            List<User> administrators = new ArrayList<>();
            List<AnimalAdvert> animalAdverts = new ArrayList<>();
            UserDao userDao = new UserDao(connection);
            AnimalAdvertDao animalAdvertDao = new AnimalAdvertDao(connection);
            for (int i = 0; i < user_ides.size(); i++) {
                administrators.add(userDao.getEntityById(user_ides.get(i)));
                animalAdverts.addAll(animalAdvertDao.getUsersAdverts(user_ides.get(i)));
            }
            shelter.setAdministrators(administrators);
            shelter.setAnimalAdverts(animalAdverts);
            return shelter;
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean delete(Shelter instance) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_SHELTER, value);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public boolean create(Shelter instance) throws DaoException {///еще адверты и юзеров отправить
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_INSERT_SHELTER,
                    instance.getPlace().getId(),
                    instance.getName());
            int result = statement.executeUpdate();
            List<User> administrators = instance.getAdministrators();
            List<AnimalAdvert> animalAdverts = instance.getAnimalAdverts();
            UserDao userDao = new UserDao(connection);
            AnimalAdvertDao animalAdvertDao = new AnimalAdvertDao(connection);
            UserShelterDao userShelterDao = new UserShelterDao(connection);
            for (int i = 0; i < administrators.size(); i++) {/////
                userDao.create(administrators.get(i));///id смещается
                userShelterDao.add(instance.getId(), administrators.get(i).getId());
            }
            for (int i = 0; i < animalAdverts.size(); i++) {
                animalAdvertDao.create(animalAdverts.get(i));
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public Shelter update(Shelter instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_UPDATE_SHELTER,
                    instance.getPlace().getId(),
                    instance.getName(),
                    instance.getId());
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return instance;
    }

    @Override
    public boolean deleteByPlaceId(int id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_BY_PLACE_SHELTER, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }
}
