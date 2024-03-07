package com.epf.rentmanager.dao;

import java.time.LocalDate ;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) FROM Client;" ;
	
	public long create(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(CREATE_CLIENT_QUERY);

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));

			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				int id = resultSet.getInt(1) ;
				client.setId(id);
				return id ;
			}

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Échec de la création du client : " + client.toString(), e);
		}
        return -1 ;
    }

	public Client findById(int id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(FIND_CLIENT_QUERY);

			ps.setInt(1, id);

			//ps.execute();

			ResultSet resultSet = ps.executeQuery();

			if(resultSet.next()){
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				return new Client(id, nom, prenom, email, naissance);
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException("Échec de la recherche du client avec l'ID : " + id, e);
		}
		return null;
	}
	
	public long delete(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY);

			ps.setInt(1,client.getId());

			return ps.executeUpdate() ;

		} catch (SQLException e) {
			throw new DaoException("Échec de la suppression du client avec l'ID : " + client.getId(), e);
		}
	}

	public List<Client> findAll() throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);

			ResultSet resultSet = ps.executeQuery();

			List<Client> listeClients = new ArrayList<>();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				listeClients.add(new Client(id, nom, prenom, email, naissance)) ;
			}
			return listeClients ;

		} catch (SQLException e) {
			throw new DaoException("Échec de la récupération de la liste des clients", e);
		}
	}

	public int countClients() throws DaoException {
		int count = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_CLIENTS_QUERY);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			resultSet.close();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Échec de la récupération du nombre de clients", e);
		}
		return count;
	}

}
