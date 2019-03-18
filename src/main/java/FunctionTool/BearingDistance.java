package FunctionTool;

public class BearingDistance {
        private Point pointB ;
        private Point pointE ;
        private double mDistance = 0.0;
        private double mInitialBearing = 0.0;
        private double mFinalBearing = 0.0;

        public BearingDistance(Point pointB, Point pointE) {
                this.pointB = pointB;
                this.pointE = pointE;
        }

        public Point getPointB() {
                return pointB;
        }

        public Point getPointE() {
                return pointE;
        }

        public double getmDistance() {
                return mDistance;
        }

        public double getmInitialBearing() {
                return mInitialBearing;
        }

        public double getmFinalBearing() {
                return mFinalBearing;
        }

        public void setmDistance(double mDistance) {
                this.mDistance = mDistance;
        }

        public void setmInitialBearing(double mInitialBearing) {
                this.mInitialBearing = mInitialBearing;
        }

        public void setmFinalBearing(double mFinalBearing) {
                this.mFinalBearing = mFinalBearing;
        }
}
