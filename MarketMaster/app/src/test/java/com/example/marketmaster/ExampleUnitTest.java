//example local unit test.
package com.example.marketmaster;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}

/*
Firstly, to prepare for the test, we need to configure the build.gradle.
android {
 
defaultConfig {
 
testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
 
//Assign a TestInstrumentationRunner for the project, which is used to run all the tests for
the project
 
} 
} 
 
//and add dependencies for instrumentation testing in build.gradle:
 
dependencies {  
 
androidTestCompile 'com.android.support:support-annotations:23.1.1'  
 
androidTestCompile 'com.android.support.test:runner:0.4.1'  
 
androidTestCompile 'com.android.support.test:rules:0.4.1'  
  }
Also, we need to add the following codes for the dependencies in build.gradle: androidTestCompile ‘com.android.support.test.espresso:espresso-core:2.2.1'
  Beside, we have to add another library in build.gradle to help androidTestCompile ‘org.hamcrest:hamcrest-library:1.3'
Secondly, the test cases are the following:
@RunWith(AndroidJUnit4.class)   
@LargeTest   
public class SignUpActivityTest extends ActivityInstrumentationTestCase2 {  
private Instrumentation mInstrumentation;   
private SignUpActivity mSignUpActivity;   
 
 private MaterialEditText mUsernameEditText;   
private MaterialEditText mSecretEditText;  
private MaterialEditText mCheckEditText;  
private MaterialEditText mPasswordEdt;   
private Button mSubmitBtn;   
public SignUpActivityTest()  { 
  super(SignUpActivity.class);   
} 
//Initialization 
 
@Before 
 
public void setUp() throws Exception {
 
super.setUp(); 
 
// @Before represents the things need to be done before executing all test cases 
 
injectInstrumentation(InstrumentationRegistry.getInstrumentation());
 
mInstrumentation = getInstrumentation(); 
 
// getActivity() will start corresponding activity before all test cases start 
 
mSignUpActivity= getActivity(); 
 
mUsernameEditText=(MaterialEditText)mSignUpActivity.findViewById(R.id.SignUp_Username); 
 
mSecretEditText=(MaterialEditText)mSignUpActivity.findViewById(R.id.SignUp_newpassword);
  mCheckEditText=(MaterialEditText)mSignUpActivity.findViewById(R.id.SignUp_checkpassword;   
mPasswordEdt = (MaterialEditText) mSignUpActivity.findViewById(R.id.SignUp_checknum); 
 
mSubmitBtn = (Button) mSignUpActivity.findViewById(R.id.SignUp_submit); 
 
}
//keyboard input   
public void input()  
{   
mSignUpActivity.runOnUiThread(new Runnable() 

  
{
 
@Override 
 
public void run() 
 
{ 
 
mUsernameEditText.requestFocus(); 
 
mUsernameEditText.performClick(); 
 
} 
 
}); 
 
// sync the application 
 
mInstrumentation.waitForIdleSync(); 
 
// use send keys to input user name 
 
sendKeys(KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_2,   
KeyEvent.KEYCODE_3, KeyEvent.KEYCODE_4, 
 
KeyEvent.KEYCODE_5, KeyEvent.KEYCODE_6, 
 
KeyEvent.KEYCODE_7,KeyEvent.KEYCODE_8, 
 
KeyEvent.KEYCODE_9 
); 
mSignUpActivity.runOnUiThread(new Runnable()   
{   
@Override 
public void run()   
{   
mSecretEditText.requestFocus();   
mSecretEditText.performClick();   
}   
});   
// use send keys to input password   

 sendKeys(KeyEvent.KEYCODE_0, KeyEvent.KEYCODE_0,   
KeyEvent.KEYCODE_0, KeyEvent.KEYCODE_0,   
KeyEvent.KEYCODE_0);  
mSignUpActivity.runOnUiThread(new Runnable()   
{ 
 
@Override
 
public void run() 
 
{ 
 
mCheckEditText.requestFocus(); 
 
mCheckEditText.performClick(); 
 
} 
 
}); 
 
 
// use send keys to get verification code for correct matching
sendKeys(KeyEvent.KEYCODE_0, KeyEvent.KEYCODE_0,   
KeyEvent.KEYCODE_0, KeyEvent.KEYCODE_0,   
KeyEvent.KEYCODE_0);  
}
//Login test 
   
@Test   
public void BtnTest()   
{   
input(); 
 
 
onView(withId(R.id.SignUp_get_checknum)).perform(click());   
onView(withId(R.id.SignUp_submit)).perform(click());   
}   
@Test 

 
public void mSubmitBtnTest() 
// click sign up button without inputting username   
onView(withId(R.id.SignUp_submit)).perform(click());   
}    }   
// test the validity of user information input 
   
@Test
 
public void testInput()
 
{
 
input(); 
   
//check if the username is the same as the actual input   
assertEquals("123456789", usernameTv.getText().toString()); 
 
//check if the password is the same  assertEquals("00000", passwordTv.getText().toString()); 
 
} 
 
// Login button test
 
@Test 
 
public void loginTest(){ 
  onView(withId(R.id.login)).perform(click()  
);   
}  
//Sign up button test   
@Test   
public void sign_inTest(){   
onView(withId( R.id.sign_in)).perform(click());} 

public class SimpleEntityReadWriteTest {
    private UserDao userDao;
    private TestDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        userDao = db.getUserDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        User user = TestUtil.createUser(3);
        user.setName("george");
        userDao.insert(user);
        List<User> byName = userDao.findUsersByName("george");
        assertThat(byName.get(0), equalTo(user));
    }
}
 */