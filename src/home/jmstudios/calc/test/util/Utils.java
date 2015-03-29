package home.jmstudios.calc.test.util;

import java.util.List;

import com.robotium.solo.Solo;

import home.jmstudios.calc.test.ITestStatus;
import android.view.View;
import android.widget.EditText;

public class Utils
{
	public static void block(long millisecond)
	{
		try
		{
			Thread.sleep(millisecond);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void waitFewSecond(ITestStatus testStatus, long timeout)
	{
		long beginTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - beginTime < timeout
		        && testStatus.getTestStatus())
		{
			block(100);
		}
	}

	public static EditText getEditText(Solo solo, long timeout)
	{
		long beginTime = System.currentTimeMillis();
		do
		{
			List<View> viewList = solo.getCurrentViews();
			for (final View view : viewList)
			{
				if (view instanceof EditText)
				{
					return (EditText) view;
				}
			}
		} while (System.currentTimeMillis() - beginTime < timeout);
		return null;
	}
}
