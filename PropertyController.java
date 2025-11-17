public class PropertyController {
    private Property model;
    private PropertyView view;

    public PropertyController(Property model, PropertyView view) {
        this.model = model;
        this.view = view;
    }

    public Integer getPropertyID() { return model.getID(); }
    public void setPropertyID(Integer ID) { model.setID(ID); }

    public Integer getPropertyUserID() { return model.getUserID(); }
    public void setPropertyUserID(Integer userID) { model.setUserID(userID); }

    public String getPropertyAddress() { return model.getAddress(); }
    public void setPropertyAddress(String address) { model.setAddress(address); }

    public Integer getPropertyBedrooms() { return model.getBedrooms(); }
    public void setPropertyBedrooms(Integer bedrooms) { model.setBedrooms(bedrooms); }

    public Integer getPropertyBathrooms() { return model.getBathrooms(); }
    public void setPropertyBathrooms(Integer bathrooms) { model.setBathrooms(bathrooms); }
}
