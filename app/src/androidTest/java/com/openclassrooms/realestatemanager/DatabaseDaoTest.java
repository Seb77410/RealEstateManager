package com.openclassrooms.realestatemanager;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */@RunWith(AndroidJUnit4.class)
public class DatabaseDaoTest {
/*
    // For data
    private AppDatabase appDatabase;
    private HouseSeller houseSeller = new HouseSeller("BeauChamp", "beauchamp@gmail.com");
    private InterestPoint interestPoint = new InterestPoint( 2,"Ecole");
    private Media media = new Media( "Ceci est un test", "il n'y a pas d'url","4");
    private Property property = new Property(234000,153,5,"Maison", "Trop belle", "Chez moi", "Vendu", "21/12/2020", "22/12/2020", "2", "1");


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private Context instrumentationContext = InstrumentationRegistry.getInstrumentation().getContext();


    @Before
    public void initDatabase() throws Exception {
        this.appDatabase = Room.inMemoryDatabaseBuilder( instrumentationContext, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        appDatabase.close();
    }

    @Test
    public void insertAndGetProperty() throws InterruptedException {

        // Test HouseSellerDAO
        this.appDatabase.houseSellerDAO().createHouseSeller(houseSeller);
        HouseSeller houseSeller1  = getValue(this.appDatabase.houseSellerDAO().getHouseSellerByName("BeauChamp"));
        assertEquals(houseSeller1.getEmail(), houseSeller.getEmail());

        // Test InterestPointDAO
        this.appDatabase.interestPointDAO().createInterestPoint(interestPoint);
        InterestPoint interestPoint1  = getValue(this.appDatabase.interestPointDAO().getInterestPointById(2));
        assertEquals(interestPoint1.getCategory(), interestPoint.getCategory());

        // Test PropertyDao
        this.appDatabase.propertyDAO().createProperty(property);
        Property property1  = getValue(this.appDatabase.propertyDAO().getPropertyByAddress("Chez moi"));
        assertEquals(property1.getType(), property.getType());



    }


    public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }

 */
}
