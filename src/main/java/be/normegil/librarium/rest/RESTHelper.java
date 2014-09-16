package be.normegil.librarium.rest;

import be.normegil.librarium.libraries.URL;
import be.normegil.librarium.model.dao.DAO;
import be.normegil.librarium.model.data.Entity;
import be.normegil.librarium.model.rest.CollectionResource;
import be.normegil.librarium.util.jaxb.adapter.UUIDToRESTURLAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.Marshaller;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

public class RESTHelper<E extends Entity> {

	private static final Logger LOG = LoggerFactory.getLogger(RESTHelper.class);
	private DAO<E> dao;
	private ContextResolver<Marshaller> context;
	private Updater<E> updater;

	public RESTHelper(@NotNull final DAO<E> dao, @NotNull final ContextResolver<Marshaller> context, @NotNull Updater<E> updater) {
		this.dao = dao;
		this.context = context;
		this.updater = updater;
	}

	public Response getAll(@NotNull final UriInfo info, final Long offset, final Integer limit) {
		try {
			URL baseURL = new URL(info.getBaseUri());
			setMarshallerOptions(baseURL);

			List<E> entities = dao.getAll();
			CollectionResource resource = CollectionResource.builder()
					.setOffset(offset)
					.setLimit(limit)
					.setTotalNumberOfItems(dao.getNumberOfElements())
					.setBaseURL(baseURL)
					.addAllItems(Entity.helper().convertToURLs(entities, baseURL))
					.build();
			return Response.ok(resource).build();
		} catch (Exception e) {
			LOG.error("Error getting all Entities [URI=" + info.getBaseUri() + "]", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	public Response get(@NotNull final UriInfo info, @NotNull final UUID id) {
		try {
			if (id == null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			Entity entity = dao.get(id);
			if (entity == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			} else {
				URL baseURL = new URL(info.getBaseUri().toURL());
				setMarshallerOptions(baseURL);
				return Response.ok(entity).build();
			}
		} catch (Exception e) {
			LOG.error("Error getting Game with ID : " + id, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	public Response create(@NotNull final UriInfo info, @NotNull final E entity) {
		try {
			if (entity == null || entity.getId() != null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			dao.create(entity);
			URL baseURL = new URL(info.getBaseUri());
			UUIDToRESTURLAdapter adapter = new UUIDToRESTURLAdapter(baseURL);
			URL urlToCreatedEntity = adapter.marshal(entity.getId());
			return Response.ok().location(urlToCreatedEntity.toURI()).build();
		} catch (Exception e) {
			LOG.error("Error creating games", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	public Response updateByPUT(@NotNull final UUID id, @NotNull final E entity) {
		try {
			if (id == null || entity == null || !id.equals(entity.getId())) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			E loadedEntity = dao.get(id);
			if (loadedEntity == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}

			updater.updateEverything(loadedEntity, entity);

			dao.update(entity);
			return Response.ok().build();
		} catch (Exception e) {
			LOG.error("Error updating Game with ID : " + id, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	public Response updateByPOST(@NotNull final UriInfo info, @NotNull final UUID id, @NotNull final E entity) {
		try {
			if (id == null || entity == null || !id.equals(entity.getId())) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			E loadedEntity = dao.get(id);
			if (loadedEntity == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}

			updater.updateNotNull(loadedEntity, entity);

			dao.update(entity);

			URL baseURL = new URL(info.getBaseUri());
			UUIDToRESTURLAdapter adapter = new UUIDToRESTURLAdapter(baseURL);
			URL urlToCreatedEntity = adapter.marshal(entity.getId());
			return Response.ok().location(urlToCreatedEntity.toURI()).build();
		} catch (Exception e) {
			LOG.error("Error updating Game with ID : " + id, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	public Response delete(@NotNull final UUID id) {
		try {
			if (id == null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}

			E entity = dao.get(id);
			if (entity == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}

			dao.remove(entity);
			return Response.ok().build();
		} catch (Exception e) {
			LOG.error("Error deleting game with ID : " + id, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private void setMarshallerOptions(final URL baseURL) throws MalformedURLException {
		Marshaller marshaller = context.getContext(this.getClass());
		marshaller.setAdapter(UUIDToRESTURLAdapter.class, new UUIDToRESTURLAdapter(baseURL));
	}
}
