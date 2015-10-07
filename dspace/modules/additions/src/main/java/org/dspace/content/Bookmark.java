package org.dspace.content;

import org.dspace.eperson.EPerson;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by tim on 06/10/15.
 */
@Entity
@Table(name="bookmark")
public class Bookmark {
	@Id
	@Column(name="bookmark_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name="title")
	private String title;

	@Column(name="date_created")
	@Temporal(TemporalType.DATE)
	private Date dateCreated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private EPerson creator;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public EPerson getCreator() {
		return creator;
	}

	public void setCreator(EPerson creator) {
		this.creator = creator;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
