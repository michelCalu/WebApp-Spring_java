package be.unamur.hermes.dataaccess.repository;

public interface ParameterRepository {

    public void create(long municipalityId, long requestTypeId, String contents);

    public void updateContents(long municipalityId, long requestTypeId, String contents);

    public String findContents(long municipalityId, long requestTypeId);

}
