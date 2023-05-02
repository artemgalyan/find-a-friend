package by.fpmibsu.findafriend.dataaccesslayer;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;

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
                .addService(UserShelterDao.class, SERVICE_LIFETIME, DbUserShelterDao.class);
    }
}
