<?php

namespace App\Entity;

use App\Repository\ComplementRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Entité représentant un Complément (frites ou boisson)
 * Correspond à la table "complements" en base de données
 * 
 * @author Étudiant 3ème année - Brasil Burger
 */
#[ORM\Entity(repositoryClass: ComplementRepository::class)]
#[ORM\Table(name: 'complements')]
class Complement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: Types::INTEGER)]
    private ?int $id = null;

    #[ORM\Column(type: Types::STRING, length: 100, nullable: true)]
    private ?string $nom = null;

    #[ORM\Column(type: Types::DECIMAL, precision: 10, scale: 2, nullable: true)]
    private ?string $prix = null;

    #[ORM\Column(type: Types::STRING, length: 255, nullable: true)]
    private ?string $image = null;

    #[ORM\Column(type: Types::BOOLEAN, options: ['default' => false])]
    private ?bool $archive = false;

    #[ORM\ManyToMany(targetEntity: Menu::class, mappedBy: 'complements')]
    private Collection $menus;

    public function __construct()
    {
        $this->menus = new ArrayCollection();
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

    public function getPrix(): ?string
    {
        return $this->prix;
    }

    public function setPrix(?string $prix): self
    {
        $this->prix = $prix;
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

    public function getMenus(): Collection
    {
        return $this->menus;
    }

    /**
     * Retourne le chemin complet de l'image
     */
    public function getImagePath(): string
    {
        if ($this->image) {
            return '/images/complements/' . $this->image;
        }
        return '/images/complements/default.jpg';
    }

    public function __toString(): string
    {
        return $this->nom ?? '';
    }
}