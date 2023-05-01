package by.fpmibsu.findafriend.dataaccesslayer;

import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;

public class DaoRegisterer {
    public static final GlobalServiceProvider.ServiceType SERVICE_LIFETIME = GlobalServiceProvider.ServiceType.SCOPED;
    public static void register(GlobalServiceProvider globalServiceProvider) {
        globalServiceProvider.addService(AdvertDao.class, SERVICE_LIFETIME, DbAdvertDao.class);
        globalServiceProvider.addService(AnimalAdvertDao.class, SERVICE_LIFETIME, DbAnimalAdvertDao.class);
        globalServiceProvider.addService(PhotoDao.class, SERVICE_LIFETIME, DbPhotoDao.class);
        globalServiceProvider.addService(PlaceDao.class, SERVICE_LIFETIME, DbPlaceDao.class);
        globalServiceProvider.addService(ShelterDao.class, SERVICE_LIFETIME, DbShelterDao.class);
        globalServiceProvider.addService(UserDao.class, SERVICE_LIFETIME, DbUserDao.class);
        globalServiceProvider.addService(UserShelterDao.class, SERVICE_LIFETIME, DbUserShelterDao.class);
    }
}
