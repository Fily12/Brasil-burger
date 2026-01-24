<?php

namespace App\Controller;

use App\Entity\Menu;
use App\Form\MenuType;
use App\Repository\MenuRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * Contrôleur pour la gestion des menus
 * 
 * Ce contrôleur gère toutes les actions CRUD (Create, Read, Update, Delete)
 * pour les menus du restaurant Brasil Burger.
 * 
 * Routes préfixées par /gestionnaire/menus
 * 
 * @author Étudiant 3ème année - Brasil Burger
 */
#[Route('/gestionnaire/menus')]
class MenuController extends AbstractController
{
    /**
     * Affiche la liste de tous les menus (interface en grille)
     * 
     * Cette méthode récupère tous les menus depuis la base de données
     * et les affiche dans une grille de cartes (3 colonnes).
     * 
     * @param MenuRepository $menuRepository Repository pour accéder aux menus
     * @return Response Page HTML avec la grille des menus
     */
    #[Route('/', name: 'app_menu_index', methods: ['GET'])]
    public function index(MenuRepository $menuRepository): Response
    {
        // Récupération de tous les menus (actifs et archivés)
        $menus = $menuRepository->findAllWithArchived();

        // Rendu du template avec la liste des menus
        return $this->render('menu/index.html.twig', [
            'menus' => $menus,
        ]);
    }

    /**
     * Affiche le formulaire de création d'un nouveau menu
     * et traite la soumission du formulaire
     * 
     * @param Request $request Requête HTTP (contient les données du formulaire)
     * @param EntityManagerInterface $entityManager Gestionnaire d'entités Doctrine
     * @return Response Page HTML avec le formulaire ou redirection après création
     */
    #[Route('/nouveau', name: 'app_menu_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        // Création d'une nouvelle instance de Menu (vide)
        $menu = new Menu();
        
        // Création du formulaire associé à l'entité Menu
        $form = $this->createForm(MenuType::class, $menu);
        
        // Traitement de la requête HTTP (remplissage du formulaire)
        $form->handleRequest($request);

        // Si le formulaire est soumis ET valide
        if ($form->isSubmitted() && $form->isValid()) {
            // Sauvegarde du menu en base de données
            $entityManager->persist($menu);
            $entityManager->flush();

            // Message de confirmation pour l'utilisateur
            $this->addFlash('success', 'Le menu a été créé avec succès.');

            // Redirection vers la liste des menus
            return $this->redirectToRoute('app_menu_index');
        }

        // Affichage du formulaire (GET ou formulaire invalide)
        return $this->render('menu/form.html.twig', [
            'menu' => $menu,
            'form' => $form,
            'isEdit' => false, // Indique qu'on est en mode création
        ]);
    }

    /**
     * Affiche le formulaire de modification d'un menu existant
     * et traite la soumission du formulaire
     * 
     * @param Request $request Requête HTTP
     * @param Menu $menu Menu à modifier (injecté automatiquement par Symfony)
     * @param EntityManagerInterface $entityManager Gestionnaire d'entités
     * @return Response Page HTML avec le formulaire ou redirection après modification
     */
    #[Route('/{id}/modifier', name: 'app_menu_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Menu $menu, EntityManagerInterface $entityManager): Response
    {
        // Création du formulaire pré-rempli avec les données du menu
        $form = $this->createForm(MenuType::class, $menu);
        
        // Traitement de la requête
        $form->handleRequest($request);

        // Si le formulaire est soumis ET valide
        if ($form->isSubmitted() && $form->isValid()) {
            // Mise à jour en base de données
            $entityManager->flush();

            // Message de confirmation
            $this->addFlash('success', 'Le menu a été modifié avec succès.');

            // Redirection vers la liste
            return $this->redirectToRoute('app_menu_index');
        }

        // Affichage du formulaire
        return $this->render('menu/form.html.twig', [
            'menu' => $menu,
            'form' => $form,
            'isEdit' => true, // Indique qu'on est en mode modification
        ]);
    }

    /**
     * Archive ou désarchive un menu
     * 
     * Cette action change l'état d'archivage du menu.
     * Un menu archivé n'est plus visible pour les clients.
     * 
     * @param Menu $menu Menu à archiver/désarchiver
     * @param EntityManagerInterface $entityManager Gestionnaire d'entités
     * @return Response Redirection vers la liste des menus
     */
    #[Route('/{id}/archiver', name: 'app_menu_archive', methods: ['POST'])]
    public function archive(Menu $menu, EntityManagerInterface $entityManager): Response
    {
        // Inverse l'état d'archivage
        $menu->setArchive(!$menu->isArchive());
        
        // Sauvegarde en base
        $entityManager->flush();

        // Message de confirmation adapté
        $message = $menu->isArchive() 
            ? 'Le menu a été archivé avec succès.' 
            : 'Le menu a été désarchivé avec succès.';
        
        $this->addFlash('success', $message);

        // Redirection vers la liste
        return $this->redirectToRoute('app_menu_index');
    }

    /**
     * Supprime définitivement un menu de la base de données
     * 
     * ATTENTION : Cette action est irréversible.
     * Il est recommandé d'utiliser l'archivage plutôt que la suppression.
     * 
     * @param Menu $menu Menu à supprimer
     * @param EntityManagerInterface $entityManager Gestionnaire d'entités
     * @return Response Redirection vers la liste des menus
     */
    #[Route('/{id}/supprimer', name: 'app_menu_delete', methods: ['POST'])]
    public function delete(Menu $menu, EntityManagerInterface $entityManager): Response
    {
        // Suppression du menu
        $entityManager->remove($menu);
        $entityManager->flush();

        // Message de confirmation
        $this->addFlash('success', 'Le menu a été supprimé définitivement.');

        // Redirection vers la liste
        return $this->redirectToRoute('app_menu_index');
    }
}