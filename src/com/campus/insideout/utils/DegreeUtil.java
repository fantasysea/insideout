package com.campus.insideout.utils;

import android.graphics.Point;
import android.graphics.PointF;


/**
 * @author genius
 * 这里的坐标系是X轴向右，Y轴向上的坐标
 */
public class DegreeUtil {

	private final static double PI = 3.1415926;
	
	enum _Quadrant{
		eQ_NONE,									//  在坐标轴
		eQ_ONE,										//  第一象限
		eQ_TWO,										//	第二象限
		eQ_THREE,									//	第三象限
		eQ_FOUR										//	第四象限
	}
	
	
	
	/**
	 * @param pointf
	 * @return
	 * 
	 * 获得Point点所在象�?
	 */
	public static _Quadrant GetQuadrant(PointF pointf){
			if (pointf.x == 0 || pointf.y == 0)
			{
				return _Quadrant.eQ_NONE;
			}
			
			if (pointf.x > 0)
			{
				if (pointf.y > 0)
				{
					return _Quadrant.eQ_ONE;
				}
				else
				{
					return _Quadrant.eQ_TWO;
				}

			}
			else
			{
				if (pointf.y < 0)
				{
					return _Quadrant.eQ_THREE;
				}
				else
				{
					return _Quadrant.eQ_FOUR;
				}
			}
	}
	
	/**
	 * @param pointf
	 * @return
	 * 
	 * 获得点所在角度（点与坐标轴原点连线与Y正半轴的顺时针夹角）单位为度
	 */
	public static float GetRadianByPos(PointF pointf){
		double dAngle = GetRadianByPosEx(pointf);
		
		return (float) (dAngle * (360 / (2 * PI)));
	}
	
	/**
	 * @param pointf
	 * @return
	 * 
	 * 获得点所在角度（点与坐标轴原点连线与Y正半轴的顺时针夹角）单位为弧
	 */
	private static double GetRadianByPosEx(PointF pointf){
		
		if (pointf.x == 0 && pointf.y == 0)
		{
			return 0;
		}


		double Sin = pointf.x / Math.sqrt(pointf.x * pointf.x + pointf.y * pointf.y);
		double dAngle = Math.asin(Sin);

		switch(GetQuadrant(pointf))
		{
		case eQ_NONE:
			{
				if (pointf.x == 0 && pointf.y == 0)
				{
					return 0;
				}

				if (pointf.x == 0)
				{
					if (pointf.y > 0)
					{
						return 0;
					}
					else
					{
						return PI;
					}
				}
				
				if (pointf.y == 0)
				{
					if (pointf.x > 0)
					{
						return PI/2;
					}
					else
					{
						return (float) (1.5*PI);
					}
				}
			}
			break;
		case eQ_ONE:
			{
				return dAngle;
			}
		case eQ_TWO:
			{
				dAngle = PI - dAngle;
			}
			break;
		case eQ_THREE:
			{
				dAngle = PI - dAngle;
			}
			break;
		case eQ_FOUR:
			{
				dAngle += 2*PI;
			}
			break;
		}

		return dAngle;
		
	}
}
