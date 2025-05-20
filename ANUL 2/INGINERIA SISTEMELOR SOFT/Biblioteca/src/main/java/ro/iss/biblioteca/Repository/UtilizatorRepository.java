package ro.iss.biblioteca.Repository;

import ro.iss.biblioteca.Domain.Utilizator;
import ro.iss.biblioteca.Utils.HibernateUtils;
import ro.iss.biblioteca.Utils.JdbcUtils;
import ro.iss.biblioteca.Validation.ValidatorUtilizator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UtilizatorRepository implements IRepository<Long, Utilizator>{
    private JdbcUtils dbUtils;

    private ValidatorUtilizator validator;

    public UtilizatorRepository(JdbcUtils dbUtils, ValidatorUtilizator validator) {
        this.dbUtils = dbUtils;
        this.validator = validator;
    }

    @Override
    public Optional<Utilizator> add(Utilizator utilizator) {
//        Connection connection = dbUtils.getConnection();
//        try(
//                PreparedStatement preparedStatement = connection.prepareStatement("insert into Utilizatori(nume, prenume, cnp, adresa, email, telefon, parola, esteBibliotecar) values (?, ?, ?, ?, ?, ?, ?, ?)")
//        ){
//            validator.validate(utilizator);
//            preparedStatement.setString(1, utilizator.getNume());
//            preparedStatement.setString(2, utilizator.getPrenume());
//            preparedStatement.setLong(3, utilizator.getCnp());
//            preparedStatement.setString(4, utilizator.getAdresa());
//            preparedStatement.setString(5, utilizator.getEmail());
//            preparedStatement.setString(6, utilizator.getTelefon());
//            preparedStatement.setString(7, utilizator.getParola());
//            preparedStatement.setBoolean(8, utilizator.getEsteBibliotecar());
//
//            int result = preparedStatement.executeUpdate();
//            if (result == 0)
//                return Optional.of(utilizator);
//            else
//                return Optional.empty();
//
//        } catch (SQLException e) {
//            if (e.getMessage().contains("email"))
//                throw new RuntimeException("Emailul exista deja.");
//            else
//                if (e.getMessage().contains("cnp"))
//                    throw new RuntimeException("Cnp-ul exista deja.");
//                else
//                    throw new RuntimeException(e.getMessage());
//        }
        try {
            HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(utilizator));
        } catch (Exception e) {
                if (e.getMessage().contains("email"))
                    throw new RuntimeException("Emailul exista deja.");
                else
                    if (e.getMessage().contains("cnp"))
                        throw new RuntimeException("Cnp-ul exista deja.");
                    else
                        throw new RuntimeException(e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Utilizator> findUserByEmailAndPassword(String email, String parola){
        Connection connection = dbUtils.getConnection();
        try(
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Utilizatori where email = ? and parola = ?")
        ){
            Utilizator utilizator = new Utilizator("a", "b", 1234567891234L, "artelor", email, "0734198491", parola, false);
            validator.validate(utilizator);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, parola);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    Long idUtilizator = resultSet.getLong("idUtilizator");
                    String nume = resultSet.getString("nume");
                    String prenume = resultSet.getString("prenume");
                    Long cnp = resultSet.getLong("cnp");
                    String adresa = resultSet.getString("adresa");
                    String email1 = resultSet.getString("email");
                    String telefon = resultSet.getString("telefon");
                    String parola1 = resultSet.getString("parola");
                    Boolean esteBibliotecar = resultSet.getBoolean("esteBibliotecar");

                    Utilizator utilizatorGasit = new Utilizator(nume, prenume, cnp, adresa, email1, telefon, parola1, esteBibliotecar);
                    utilizatorGasit.setIdUtilizator(idUtilizator);
                    return Optional.of(utilizatorGasit);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Utilizator> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Utilizator> update(Utilizator utilizator) {
        return Optional.empty();
    }

    @Override
    public Optional<Utilizator> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Utilizator> getAll() {
        return null;
    }

    @Override
    public Long size() {
        return null;
    }

}
