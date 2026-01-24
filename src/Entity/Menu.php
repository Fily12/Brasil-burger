<?php

namespace App\Entity;

use App\Repository\MenuRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Entité représentant un Menu (burger + frites + boisson)
 * Correspond à la table "menus" en base de données
 * Le prix du menu est calculé comme la somme des prix de ses éléments
 * 
 * @author Étudiant 3ème année - Brasil Burger
 */
#[ORM\Entity(repositoryClass: MenuRepository::class)]
#[ORM\Table(name: 'menus')]
class Menu
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: Types::INTEGER)]
    private ?int $id = null;

    #[ORM\Column(type: Types::STRING, length: 100, nullable: true)]
    private ?string $nom = null;

    #[ORM\Column(type: Types::STRING, length: 255, nullable: true)]
    private ?string $image = null;

    #[ORM\Column(type: Types::BOOLEAN, options: ['default' => false])]
    private ?bool $archive = false;

    /**
     * Collection des burgers composant ce menu
     * Relation Many-to-Many (via table menu_burgers)
     */
    #[ORM\ManyToMany(targetEntity: Burger::class)]
    #[ORM\JoinTable(name: 'menu_burgers')]
    #[ORM\JoinColumn(name: 'menu_id', referencedColumnName: 'id')]
    #[ORM\InverseJoinColumn(name: 'burger_id', referencedColumnName: 'id')]
    private Collection $burgers;

    /**
     * Collection des compléments (frites + boissons) composant ce menu
     * Relation Many-to-Many (via table menu_complements)
     */
    #[ORM\ManyToMany(targetEntity: Complement::class, inversedBy: 'menus')]
    #[ORM\JoinTable(name: 'menu_complements')]
    #[ORM\JoinColumn(name: 'menu_id', referencedColumnName: 'id')]
    #[ORM\InverseJoinColumn(name: 'complement_id', referencedColumnName: 'id')]
    private Collection $complements;

    public function __construct()
    {
        $this->burgers = new ArrayCollection();
        $this->complements = new ArrayCollection();
    }

    // ========================================
    // GETTERS ET SETTERS
    // ========================================

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(?string $nom): self
    {
        $this->nom = $nom;
        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(?string $image): self
    {
        $this->image = $image;
        return $this;
    }

    public function isArchive(): ?bool
    {
        return $this->archive;
    }

    public function setArchive(bool $archive): self
    {
        $this->archive = $archive;
        return $this;
    }

    public function getBurgers(): Collection
    {
        return $this->burgers;
    }

    public function addBurger(Burger $burger): self
    {
        if (!$this->burgers->contains($burger)) {
            $this->burgers->add($burger);
        }
        return $this;
    }

    public function removeBurger(Burger $burger): self
    {
        $this->burgers->removeElement($burger);
        return $this;
    }

    public function getComplements(): Collection
    {
        return $this->complements;
    }

    public function addComplement(Complement $complement): self
    {
        if (!$this->complements->contains($complement)) {
            $this->complements->add($complement);
        }
        return $this;
    }

    public function removeComplement(Complement $complement): self
    {
        $this->complements->removeElement($complement);
        return $this;
    }

    // ========================================
    // MÉTHODES MÉTIER
    // ========================================

    /**
     * Calcule le prix total du menu
     * Somme des prix des burgers + compléments
     */
    public function getPrixTotal(): float
    {
        $total = 0;

        // Somme des prix des burgers
        foreach ($this->burgers as $burger) {
            $total += (float) $burger->getPrix();
        }

        // Somme des prix des compléments
        foreach ($this->complements as $complement) {
            $total += (float) $complement->getPrix();
        }

        return $total;
    }

    /**
     * Retourne une description courte du menu
     * Liste des burgers et compléments inclus (tronqué si trop long)
     */
    public function getDescription(): string
    {
        $elements = [];

        foreach ($this->burgers as $burger) {
            $elements[] = $burger->getNom();
        }

        foreach ($this->complements as $complement) {
            $elements[] = $complement->getNom();
        }

        $description = implode(', ', $elements);
        
        // Tronquer à 80 caractères comme sur la maquette
        if (strlen($description) > 80) {
            return substr($description, 0, 77) . '...';
        }
        
        return $description;
    }

    /**
     * Retourne le chemin complet de l'image
     */
    public function getImagePath(): string
    {
        if ($this->image) {
            return '/images/menus/' . $this->image;
        }
        return '/images/menus/default.jpg';
    }

    public function __toString(): string
    {
        return $this->nom ?? '';
    }
}