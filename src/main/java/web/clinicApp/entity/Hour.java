package web.clinicApp.entity;

public enum Hour {
    SEVEN_HALF("07:30"),
    EIGHT("08:00"),
    EIGHT_HALF("08:30"),
    NINE("09:00"),
    NINE_HALF("09:30"),
    TEN("10:00"),
    TEN_HALF("10:30"),
    ELEVEN("11:00"),
    ELEVEN_HALF("11:30"),
    TWELVE("12:00"),
    TWELVE_HALF("12:30"),
    THIRTEEN("13:00"),
    THIRTEEN_HALF("13:30"),
    FOURTEEN("14:00"),
    FOURTEEN_HALF("14:30"),
    FIFTEEN("15:00"),
    FIFTEEN_HALF("15:30"),
    SIXTEEN("16:00"),
    SIXTEEN_HALF("16:30"),
    SEVENTEEN("17:00"),
    SEVENTEEN_HALF("17:30"),
    EIGHTEEN("18:00"),
    EIGHTEEN_HALF("18:30"),
    NINETEEN("19:00"),
    NINETEEN_HALF("19:30");


    // declaring private variable for getting values
    private final String label;

    // getter method
    public String getLabel()
    {
        return this.label;
    }

    // enum constructor - cannot be public or protected
    Hour(String label)
    {
        this.label = label;
    }
}
