package org.dspace.content;

import org.dspace.eperson.EPerson;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="bookmark")
public class Bookmark {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "bookmark_id", unique = true, nullable = false, insertable = true, updatable = false)
    private java.util.UUID uuid;

	@Column(name="title")
	private String title;

	@Column(name="date_created")
	@Temporal(TemporalType.DATE)
	private Date dateCreated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator")
    private EPerson creator;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item")
    private Item item;

    protected Bookmark(){

    }
	public UUID getId() {
		return uuid;
	}

	public void setId(UUID id) {
		this.uuid = id;
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
