package be.unamur.hermes.dataaccess.entity;

import java.util.Arrays;
import java.util.Objects;

public class RequestField {

    private String code;
    private Boolean required;
    private String fieldType;
    private String fieldValue;
    private byte[] fieldFile;
    private String fieldFileType;

    public RequestField(){};

    public RequestField(String code,
                        Boolean required,
                        String fieldType,
                        String fieldValue,
                        byte[] fieldFile,
                        String fieldFileType){
        this.code = code;
        this.required = required;
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
        this.fieldFile = fieldFile;
        this.fieldFileType = fieldFileType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public byte[] getFieldFile() {
        return fieldFile;
    }

    public void setFieldFile(byte[] fieldFile) {
        this.fieldFile = fieldFile;
    }

    public String getFieldFileType() {
        return fieldFileType;
    }

    public void setFieldFileType(String fieldFileType) {
        this.fieldFileType = fieldFileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestField)) return false;
        RequestField that = (RequestField) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(required, that.required) &&
                Objects.equals(fieldType, that.fieldType) &&
                Objects.equals(fieldValue, that.fieldValue) &&
                Arrays.equals(fieldFile, that.fieldFile) &&
                Objects.equals(fieldFileType, that.fieldFileType);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(code, required, fieldType, fieldValue, fieldFileType);
        result = 31 * result + Arrays.hashCode(fieldFile);
        return result;
    }

    @Override
    public String toString() {
        return "RequestField{" +
                ", code='" + code + '\'' +
                ", required=" + required +
                ", fieldType='" + fieldType + '\'' +
                ", value='" + fieldValue + '\'' +
                ", fileType=" + fieldFileType +
                '}';
    }
}
