package ro.iss.biblioteca.Repository;

import ro.iss.biblioteca.Domain.Carte;
import ro.iss.biblioteca.Utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarteRepository implements IRepository<Long, Carte> {

    private JdbcUtils jdbcUtils;

    public CarteRepository(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    @Override
    public Optional<Carte> add(Carte carte) {
        return Optional.empty();
    }

    @Override
    public Optional<Carte> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Carte> update(Carte carte) {
        return Optional.empty();
    }

    @Override
    public Optional<Carte> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Carte> getAll() {
        return null;
    }

    @Override
    public Long size() {
        return null;
    }

    public Iterable<Carte> getAllCartiDisponibile(){
        Connection connection = jdbcUtils.getConnection();
        List<Carte> cartiDisponibile = new ArrayList<>();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("select * from Carti where idCarte not in (select idCarte from Cosuri) and idCarte not in (select idCarte from Imprumuturi)");
        ) {
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    Long idCarte = result.getLong("idCarte");
                    String nume = result.getString("nume");
                    String autor = result.getString("autor");

                    Carte carte = new Carte(nume, autor);
                    carte.setIdCarte(idCarte);
                    cartiDisponibile.add(carte);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
        return cartiDisponibile;
    }
}
