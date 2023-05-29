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
                .addService(AdvertDao.class, SERVICE_LIFETIME, DbAdvertDao.class)
                .addService(AnimalAdvertDao.class, SERVICE_LIFETIME, DbAnimalAdvertDao.class)
                .addService(PhotoDao.class, SERVICE_LIFETIME, DbPhotoDao.class)
                .addService(PlaceDao.class, SERVICE_LIFETIME, DbPlaceDao.class)
                .addService(ShelterDao.class, SERVICE_LIFETIME, DbShelterDao.class)
                .addService(UserDao.class, SERVICE_LIFETIME, DbUserDao.class)
                .addService(UserShelterDao.class, SERVICE_LIFETIME, DbUserShelterDao.class)
                .addService(ValidTokensDao.class, SERVICE_LIFETIME, DbValidTokensDao.class);
    }
}
