package be.unamur.hermes.dataaccess.entity;

import java.util.Arrays;
import java.util.Objects;

public class RequestField {

    private String code;
    private Boolean required;
    private String fieldType;
    private String value;
    private byte[] file;

    public RequestField(){};

    public RequestField(String code,
                        Boolean required,
                        String fieldType,
                        String value,
                        byte[] file){
        this.code = code;
        this.required = required;
        this.fieldType = fieldType;
        this.value = value;
        this.file = file;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestField)) return false;
        RequestField that = (RequestField) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(required, that.required) &&
                Objects.equals(fieldType, that.fieldType) &&
                Objects.equals(value, that.value) &&
                Arrays.equals(file, that.file);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(code, required, fieldType, value);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }

    @Override
    public String toString() {
        return "RequestField{" +
                ", code='" + code + '\'' +
                ", required=" + required +
                ", fieldType='" + fieldType + '\'' +
                ", value='" + value + '\'' +
                ", file=" + Arrays.toString(file) +
                '}';
    }
}
