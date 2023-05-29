package by.fpmibsu.findafriend.dataaccesslayer;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.findafriend.dataaccesslayer.advert.AdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.advert.DbAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.DbAnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.photo.DbPhotoDao;
import by.fpmibsu.findafriend.dataaccesslayer.photo.PhotoDao;
import by.fpmibsu.findafriend.dataaccesslayer.place.DbPlaceDao;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.DbShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.user.DbUserDao;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.DbUserShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.validtokens.DbValidTokensDao;
import by.fpmibsu.findafriend.dataaccesslayer.validtokens.ValidTokensDao;

import java.sql.Connection;

public class DaoSetup extends Setup {
    public static final GlobalServiceProvider.ServiceType SERVICE_LIFETIME = GlobalServiceProvider.ServiceType.SCOPED;

    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.getServiceProvider()
                .addService(AdvertDao.class, SERVICE_LIFETIME, (s) -> new DbAdvertDao(s.getRequiredService(Connection.class)))
                .addService(AnimalAdvertDao.class, SERVICE_LIFETIME, (s) -> new DbAnimalAdvertDao(s.getRequiredService(Connection.class)))
                .addService(PhotoDao.class, SERVICE_LIFETIME, (s) -> new DbPhotoDao(s.getRequiredService(Connection.class)))
                .addService(PlaceDao.class, SERVICE_LIFETIME, (s) -> new DbPlaceDao(s.getRequiredService(Connection.class)))
                .addService(ShelterDao.class, SERVICE_LIFETIME, (s) -> new DbShelterDao(s.getRequiredService(Connection.class)))
                .addService(UserDao.class, SERVICE_LIFETIME, (s) -> new DbUserDao(s.getRequiredService(Connection.class)))
                .addService(UserShelterDao.class, SERVICE_LIFETIME, (s) -> new DbUserShelterDao(s.getRequiredService(Connection.class)))
                .addService(ValidTokensDao.class, SERVICE_LIFETIME, (s) -> new DbValidTokensDao(s.getRequiredService(Connection.class)));
    }
}
