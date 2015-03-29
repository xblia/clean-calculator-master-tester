package home.jmstudios.calc.test;

import home.jmstudios.calc.Main;
import home.jmstudios.calc.R;
import home.jmstudios.calc.test.util.Utils;
import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.SlidingDrawer;

import com.robotium.solo.Solo;

public class TestCalc extends ActivityInstrumentationTestCase2<Main> implements ITestStatus
{
	private Solo mSolo;
	private EditText mResultEdit;
	private boolean mBStatus = false;

	@SuppressLint("NewApi")
	public TestCalc()
    {
	    super(Main.class);
    }
	
	@Override
	public void setUp()
	{
		try
        {
	        super.setUp();
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
		mSolo = new Solo(this.getInstrumentation(), getActivity());
		mResultEdit = (EditText)mSolo.getView(R.id.editText1);
		mBStatus = false;
	}
	
	public void testBaseCalc() throws InterruptedException
	{
		mSolo.clickOnButton("7");
		mSolo.clickOnButton("*");
		mSolo.clickOnButton("8");
		mSolo.clickOnButton("=");
		mSolo.clickOnButton("+");
		mSolo.clickOnButton("3");
		mSolo.clickOnButton("=");
		String strResult = mResultEdit.getText().toString();
		assertEquals("59", strResult);
	}
	
	public void testChangePrecision()
	{
	    mSolo.sendKey(KeyEvent.KEYCODE_MENU);
	    mSolo.clickOnMenuItem("Settings");
	    mSolo.clickInList(4);
	    
	    EditText editText = Utils.getEditText(mSolo, 1000);
    	assertEquals(editText == null, false);
	    mSolo.clearEditText(editText);
    	mSolo.enterText(editText, "5");
    	
      	mSolo.clickOnButton("È·¶¨");
      	mSolo.sendKey(KeyEvent.KEYCODE_BACK);
      	mSolo.clickOnButton("1");
      	mSolo.clickOnButton("/");
      	mSolo.clickOnButton("3");
      	mSolo.clickOnButton("=");
      	
      	String strResult = mResultEdit.getText().toString();
		assertEquals("0.33333", strResult);
	}
	
    @SuppressLint("NewApi") 
	public void testTrigonometric()
	{
		final SlidingDrawer slidingDrawer = (SlidingDrawer)mSolo.getView(R.id.slidingDrawer1);
		controlSlidingDrawer(slidingDrawer, true);
		mSolo.clickOnButton("sin");
		controlSlidingDrawer(slidingDrawer, false);
		
		mSolo.clickOnButton("9");
		mSolo.clickOnButton("0");
		mSolo.clickOnButton("=");
		String strResult = mResultEdit.getText().toString();
		assertEquals("1", strResult);
	}
	
	@Override
	public void tearDown()
	{
		try
        {
			Utils.block(2000);
	        mSolo.finalize();
	        mSolo.finishOpenedActivities();
	        super.tearDown();
	        mBStatus = false;
        } catch (Throwable e)
        {
	        e.printStackTrace();
        }
	}
	
	public void controlSlidingDrawer(final SlidingDrawer slidingDrawer, final boolean isOpen)
	{
		mBStatus = false;
		slidingDrawer.post(new Runnable()
		{
			@Override
			public void run()
			{
				if(isOpen)
				{
					slidingDrawer.open();
				}else
				{
					slidingDrawer.close();
				}
				mBStatus = true;
			}
		});
		Utils.waitFewSecond(this, 2000);
	}

	@Override
    public boolean getTestStatus()
    {
	    return mBStatus;
    }
}