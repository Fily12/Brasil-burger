<?php

namespace App\Repository;

use App\Entity\Menu;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * Repository pour l'entité Menu
 * 
 * Ce repository contient les méthodes de requête personnalisées
 * pour récupérer les menus depuis la base de données.
 * 
 * @author Étudiant 3ème année - Brasil Burger
 */
class MenuRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Menu::class);
    }

    /**
     * Récupère tous les menus NON archivés
     * Utilisé pour l'affichage dans l'interface gestionnaire
     * 
     * @return Menu[] Liste des menus actifs
     */
    public function findAllActive(): array
    {
        return $this->createQueryBuilder('m')
            ->where('m.archive = :archive')
            ->setParameter('archive', false)
            ->orderBy('m.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }

    /**
     * Récupère tous les menus (archivés et non archivés)
     * Utilisé pour l'administration complète
     * 
     * @return Menu[] Liste de tous les menus
     */
    public function findAllWithArchived(): array
    {
        return $this->createQueryBuilder('m')
            ->orderBy('m.archive', 'ASC')
            ->addOrderBy('m.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }

    /**
     * Recherche des menus par nom
     * Utile pour un système de recherche dans l'interface
     * 
     * @param string $search Terme de recherche
     * @return Menu[] Liste des menus correspondants
     */
    public function searchByName(string $search): array
    {
        return $this->createQueryBuilder('m')
            ->where('LOWER(m.nom) LIKE LOWER(:search)')
            ->setParameter('search', '%' . $search . '%')
            ->orderBy('m.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }

    /**
     * Compte le nombre de menus actifs
     * Utile pour les statistiques du tableau de bord
     * 
     * @return int Nombre de menus actifs
     */
    public function countActive(): int
    {
        return (int) $this->createQueryBuilder('m')
            ->select('COUNT(m.id)')
            ->where('m.archive = :archive')
            ->setParameter('archive', false)
            ->getQuery()
            ->getSingleScalarResult();
    }

    /**
     * Sauvegarde un menu en base de données
     * 
     * @param Menu $menu Menu à sauvegarder
     * @param bool $flush Effectuer le flush immédiatement (par défaut true)
     */
    public function save(Menu $menu, bool $flush = true): void
    {
        $this->getEntityManager()->persist($menu);
        
        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    /**
     * Supprime un menu de la base de données
     * 
     * @param Menu $menu Menu à supprimer
     * @param bool $flush Effectuer le flush immédiatement (par défaut true)
     */
    public function remove(Menu $menu, bool $flush = true): void
    {
        $this->getEntityManager()->remove($menu);
        
        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
}
