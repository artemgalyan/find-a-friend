package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.application.serviceproviders.GlobalServiceProvider;

public class DaoRegisterer {
    public static final GlobalServiceProvider.ServiceType SERVICE_LIFETIME = GlobalServiceProvider.ServiceType.SCOPED;
    public static void register(GlobalServiceProvider globalServiceProvider) {
        globalServiceProvider.addService(AdvertDaoInterface.class, SERVICE_LIFETIME, AdvertDao.class);
        globalServiceProvider.addService(AnimalAdvertDaoInterface.class, SERVICE_LIFETIME, AnimalAdvertDao.class);
        globalServiceProvider.addService(PhotoDaoInterface.class, SERVICE_LIFETIME, PhotoDao.class);
        globalServiceProvider.addService(PlaceDaoInterface.class, SERVICE_LIFETIME, PlaceDao.class);
        globalServiceProvider.addService(ShelterDaoInterface.class, SERVICE_LIFETIME, ShelterDao.class);
        globalServiceProvider.addService(UserDaoInterface.class, SERVICE_LIFETIME, UserDao.class);
        globalServiceProvider.addService(UserShelterDaoInterface.class, SERVICE_LIFETIME, UserShelterDao.class);
    }
}
