<?php

namespace App\Repository;

use App\Entity\Complement;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * Repository pour l'entité Complement
 * 
 * Ce repository contient les méthodes de requête personnalisées
 * pour récupérer les compléments (frites, boissons) depuis la base de données.
 * 
 * @author Étudiant 3ème année - Brasil Burger
 */
class ComplementRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Complement::class);
    }

    /**
     * Récupère tous les compléments NON archivés
     * Utilisé pour les sélections dans les formulaires de menu
     * 
     * @return Complement[] Liste des compléments actifs
     */
    public function findAllActive(): array
    {
        return $this->createQueryBuilder('c')
            ->where('c.archive = :archive')
            ->setParameter('archive', false)
            ->orderBy('c.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }

    /**
     * Récupère tous les compléments (archivés et non archivés)
     * 
     * @return Complement[] Liste de tous les compléments
     */
    public function findAllWithArchived(): array
    {
        return $this->createQueryBuilder('c')
            ->orderBy('c.archive', 'ASC')
            ->addOrderBy('c.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }

    /**
     * Sauvegarde un complément en base de données
     * 
     * @param Complement $complement Complément à sauvegarder
     * @param bool $flush Effectuer le flush immédiatement
     */
    public function save(Complement $complement, bool $flush = true): void
    {
        $this->getEntityManager()->persist($complement);
        
        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    /**
     * Supprime un complément de la base de données
     * 
     * @param Complement $complement Complément à supprimer
     * @param bool $flush Effectuer le flush immédiatement
     */
    public function remove(Complement $complement, bool $flush = true): void
    {
        $this->getEntityManager()->remove($complement);
        
        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
}