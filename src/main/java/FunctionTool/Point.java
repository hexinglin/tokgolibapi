package FunctionTool;

import exception.ErrorCode;
import exception.TOKGOException;

public class Point {
    private double longitude = 0.0;
    private double latitude = 0.0;



    public static Point ZEROPOINT(){
        try {
            return new Point(0.0,0.0);
        } catch (TOKGOException e) {
            return null;
        }
    }


    public Point(double longitude, double latitude) throws TOKGOException {
        this.longitude = longitude;
        this.latitude = latitude;
        check();
    }

    private void check() throws TOKGOException {
        if (latitude >= 90.0 || latitude <=-90.0){
            double temp;
            temp = latitude;
            latitude = longitude;
            longitude = temp;
        }

        if (latitude >= 90.0 || latitude <=-90.0||longitude >= 180.0 || longitude <=-180.0){
            throw new TOKGOException(ErrorCode.PARAM_ERROR);
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
