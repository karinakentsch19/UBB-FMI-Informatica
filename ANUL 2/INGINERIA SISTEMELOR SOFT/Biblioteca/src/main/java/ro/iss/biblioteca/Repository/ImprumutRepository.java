package ro.iss.biblioteca.Repository;

import org.hibernate.Session;
import ro.iss.biblioteca.Domain.Carte;
import ro.iss.biblioteca.Domain.Cos;
import ro.iss.biblioteca.Domain.Imprumut;
import ro.iss.biblioteca.Domain.ImprumutDTO;
import ro.iss.biblioteca.Utils.HibernateUtils;
import ro.iss.biblioteca.Utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImprumutRepository implements IRepository<Long, Imprumut> {
    private JdbcUtils jdbcUtils;

    public ImprumutRepository(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public Optional<Imprumut> add(Imprumut imprumut) {
//        Connection connection = jdbcUtils.getConnection();
//        try(
//                PreparedStatement preparedStatement = connection.prepareStatement("insert into Imprumuturi(idCarte, idUtilizator, dataInchirierii) values(?, ?, ?)");
//        ){
//            preparedStatement.setLong(1, imprumut.getIdCarte());
//            preparedStatement.setLong(2, imprumut.getIdUtilizator());
//            preparedStatement.setString(3, imprumut.getDataInchirierii());
//
//            int result = preparedStatement.executeUpdate();
//            if (result == 0)
//                return Optional.of(imprumut);
//            else
//                return Optional.empty();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e.getMessage());
//        }
        try {
            HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(imprumut));
        } catch (Exception e) {
            return Optional.of(imprumut);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Imprumut> delete(Long aLong) {
//        Connection connection = jdbcUtils.getConnection();
//        try (
//                PreparedStatement preparedStatement = connection.prepareStatement("delete from Imprumuturi where idImprumut = ?")
//        ) {
//            preparedStatement.setLong(1, aLong);
//            Optional<Imprumut> imprumut = find(aLong);
//            int result = preparedStatement.executeUpdate();
//            if (result == 0)
//                return Optional.empty();
//            else
//                return imprumut;
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
//        return Optional.empty();
        Imprumut imprumut = find(aLong).get();
        if (imprumut != null) {
            HibernateUtils.getSessionFactory().inTransaction(session -> {
                session.remove(imprumut);
                session.flush();
            });
            return Optional.of(imprumut);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Imprumut> update(Imprumut imprumut) {
        return Optional.empty();
    }

    @Override
    public Optional<Imprumut> find(Long aLong) {
//        Connection connection = jdbcUtils.getConnection();
//        try (
//                PreparedStatement preparedStatement = connection.prepareStatement("select * from Imprumuturi where idImprumut = ?")
//        ) {
//            preparedStatement.setInt(1, Math.toIntExact(aLong));
//            try (ResultSet result = preparedStatement.executeQuery()) {
//                if (result.next()) {
//                    Long idImprumut = result.getLong("idImprumut");
//                    Long idCarte = result.getLong("idCarte");
//                    Long idUtilizator = result.getLong("idUtilizator");
//                    String dataInchirierii = result.getString("dataInchirierii");
//
//                    Imprumut imprumut = new Imprumut(idCarte, idUtilizator, dataInchirierii);
//                    imprumut.setIdImprumut(idImprumut);
//                    return Optional.of(imprumut);
//                }
//            }
//            return Optional.empty();
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
//        return Optional.empty();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createSelectionQuery("from Imprumut where idImprumut=:id", Imprumut.class)
                    .setParameter("id", aLong)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Iterable<Imprumut> getAll() {
        return null;
    }

    @Override
    public Long size() {
        return null;
    }

    public Iterable<ImprumutDTO> getAllCartiImprumutate() {
        Connection connection = jdbcUtils.getConnection();
        List<ImprumutDTO> cartiImprumutate = new ArrayList<>();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("select I.idImprumut, I.idCarte, I.idUtilizator, C.nume as NumeCarte, C.autor, U.nume, U.prenume from Imprumuturi I join Carti C on I.idCarte = C.idCarte join Utilizatori U on U.idUtilizator = I.idUtilizator");
        ) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    Long idImprumut = result.getLong("idImprumut");
                    Long idCarte = result.getLong("idCarte");
                    Long idUtilziator = result.getLong("idUtilizator");
                    String numeCarte = result.getString("NumeCarte");
                    String autor = result.getString("autor");
                    String numeUtilizator = result.getString("nume");
                    String prenumeUtilizator = result.getString("prenume");

                    ImprumutDTO imprumutDTO = new ImprumutDTO(idImprumut, idCarte, idUtilziator, numeCarte, autor, numeUtilizator, prenumeUtilizator);
                    cartiImprumutate.add(imprumutDTO);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
        return cartiImprumutate;
    }
}
