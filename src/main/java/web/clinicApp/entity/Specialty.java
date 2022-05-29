package web.clinicApp.entity;

public enum Specialty {

    ALLERGY_AND_IMMUNOLOGY("Allergy and immunology"),
    ANESTHESIOLOGY("Anesthesiology"),
    DERMATOLOGY("Dermatology"),
    DIAGNOSTIC_RADIOLOGY("Diagnostic Radiology"),
    EMERGENCY_MEDICINE("Emergency Medicine"),
    FAMILY_MEDICINE("Family Medicine"),
    INTERNAL_MEDICINE("Internal Medicine"),
    MEDICAL_GENETICS("Medical Genetics"),
    NEUROLOGY("Neurology"),
    NUCLEAR_MEDICINE("Nuclear Medicine"),
    OBSTETRICS_AND_GYNECOLOGY("Obstetrics and Gynecology"),
    OPHTHALMOLOGY("Ophthalmology"),
    PATHOLOGY("Pathology"),
    PEDIATRICS("Pediatrics"),
    PHYSICAL_MEDICINE_AND_REHABILITATION("Physical Medicine and Rehabilitation"),
    PREVENTIVE_MEDICINE("Preventive Medicine"),
    PSYCHIATRY("Psychiatry"),
    RADIATION_ONCOLOGY("Radiation Oncology"),
    SURGERY("Surgery"),
    UROLOGY("Urology");

    // declaring private variable for getting values
    private final String label;

    // getter method
    public String getLabel()
    {
        return this.label;
    }

    // enum constructor - cannot be public or protected
    Specialty(String label)
    {
        this.label = label;
    }

}
