package ro.iss.biblioteca.Repository;

import org.hibernate.Session;
import ro.iss.biblioteca.Domain.Carte;
import ro.iss.biblioteca.Domain.Cos;
import ro.iss.biblioteca.Utils.HibernateUtils;
import ro.iss.biblioteca.Utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CosRepository implements IRepository<Long, Cos> {

    private JdbcUtils jdbcUtils;

    public CosRepository(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public Optional<Cos> add(Cos cos) {
//        Connection connection = jdbcUtils.getConnection();
//        try (
//                PreparedStatement preparedStatement = connection.prepareStatement("insert into Cosuri(idCarte, idUtilizator, dataPlasarii) values(?, ?, ?)");
//        ) {
//            preparedStatement.setLong(1, cos.getIdCarte());
//            preparedStatement.setLong(2, cos.getIdUtilizator());
//            preparedStatement.setString(3, cos.getDataPlasarii());
//
//            int result = preparedStatement.executeUpdate();
//            if (result == 0)
//                return Optional.of(cos);
//            else
//                return Optional.empty();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e.getMessage());
//        }
        try {
            HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(cos));
        } catch (Exception e) {
            return Optional.of(cos);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Cos> delete(Long aLong) {
//        Connection connection = jdbcUtils.getConnection();
//        try (
//                PreparedStatement preparedStatement = connection.prepareStatement("delete from Cosuri where idCos = ?")
//        ) {
//            preparedStatement.setLong(1, aLong);
//            Optional<Cos> cos = find(aLong);
//            int result = preparedStatement.executeUpdate();
//            if (result == 0)
//                return Optional.empty();
//            else
//                return cos;
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
//        return Optional.empty();
        Cos cos = find(aLong).get();
        if (cos != null) {
            HibernateUtils.getSessionFactory().inTransaction(session -> {
                session.remove(cos);
                session.flush();
            });
            return Optional.of(cos);
        }
        return Optional.empty();
    }

    public void deleteCarteDinCosulUnuiUtilizator(Long idCarte, Long idUtilziator) {
        Connection connection = jdbcUtils.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("delete from Cosuri where idCarte = ? and idUtilizator = ?")
        ) {
            preparedStatement.setLong(1, idCarte);
            preparedStatement.setLong(2, idUtilziator);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
    }

    @Override
    public Optional<Cos> update(Cos cos) {
        return Optional.empty();
    }

    @Override
    public Optional<Cos> find(Long aLong) {
//        Connection connection = jdbcUtils.getConnection();
//        try (
//                PreparedStatement preparedStatement = connection.prepareStatement("select * from Cosuri where idCos = ?")
//        ) {
//            preparedStatement.setInt(1, Math.toIntExact(aLong));
//            try (ResultSet result = preparedStatement.executeQuery()) {
//                if (result.next()) {
//                    Long idCos = result.getLong("idCos");
//                    Long idCarte = result.getLong("idCarte");
//                    Long idUtilizator = result.getLong("idUtilizator");
//                    String dataPlasarii = result.getString("dataPlasarii");
//
//                    Cos cos = new Cos(idCarte, idUtilizator, dataPlasarii);
//                    cos.setIdCos(idCos);
//                    return Optional.of(cos);
//                }
//            }
//            return Optional.empty();
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
//        return Optional.empty();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createSelectionQuery("from Cos where idCos=:id", Cos.class)
                    .setParameter("id", aLong)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Iterable<Cos> getAll() {
        return null;
    }

    @Override
    public Long size() {
        return null;
    }

    public Iterable<Carte> getAllCartiDinCosulUnuiUtilizator(Long idUtilizator) {
        Connection connection = jdbcUtils.getConnection();
        List<Carte> cartiDinCos = new ArrayList<>();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Carti C join Cosuri Co on C.idCarte = Co.idCarte where Co.idUtilizator = ?");
        ) {
            preparedStatement.setLong(1, idUtilizator);
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    Long idCarte = result.getLong("idCarte");
                    String nume = result.getString("nume");
                    String autor = result.getString("autor");

                    Carte carte = new Carte(nume, autor);
                    carte.setIdCarte(idCarte);
                    cartiDinCos.add(carte);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
        return cartiDinCos;
    }
}
