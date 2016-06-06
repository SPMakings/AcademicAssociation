package app.tutor.com.tutorapps.pojo;

/**
 * Created by apple on 13/05/16.
 */
public class ContactUSDType {

    private float lat = 0.00f, lon = 0.00f;
    private String shopName = "";
    private String phoneNo = "";
    private int ImageID = 0;

    public ContactUSDType() {
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
