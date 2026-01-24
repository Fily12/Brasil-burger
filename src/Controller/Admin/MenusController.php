<?php

namespace App\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin', name: 'admin_')]
class MenusController extends AbstractController
{
    #[Route('/menus', name: 'menus')]
    public function index(): Response
    {
        // Données de test pour les menus
        $menus = [
            [
                'id' => 1,
                'nom' => 'Burger Classic',
                'description' => 'Burger traditionnel avec steak, salade, tomate',
                'prix' => 12.50,
                'prixTotal' => 12500,
                'imagePath' => 'images/burger1.jpg',
                'archive' => false
            ],
            [
                'id' => 2,
                'nom' => 'Burger Deluxe',
                'description' => 'Burger premium avec bacon et fromage',
                'prix' => 15.90,
                'prixTotal' => 15900,
                'imagePath' => 'images/burger2.jpg',
                'archive' => false
            ]
        ];

        return $this->render('admin/menus/index.html.twig', [
            'menus' => $menus
        ]);
    }

    #[Route('/menus/new', name: 'menu_new', methods: ['GET', 'POST'])]
    public function new(Request $request): Response
    {
        if ($request->isMethod('POST')) {
            $name = $request->request->get('name');
            $description = $request->request->get('description');
            $price = $request->request->get('price');
            
            // Ici vous ajouteriez la logique pour sauvegarder en base
            $this->addFlash('success', 'Menu créé avec succès!');
            return $this->redirectToRoute('admin_menus');
        }
        
        return $this->render('admin/menus/new.html.twig');
    }

    #[Route('/menus/{id}/edit', name: 'menu_edit', methods: ['GET', 'POST'])]
    public function edit(int $id, Request $request): Response
    {
        if ($request->isMethod('POST')) {
            $name = $request->request->get('name');
            $description = $request->request->get('description');
            $price = $request->request->get('price');
            
            // Ici vous ajouteriez la logique pour mettre à jour en base
            $this->addFlash('success', 'Menu modifié avec succès!');
            return $this->redirectToRoute('admin_menus');
        }
        
        return $this->render('admin/menus/edit.html.twig', ['id' => $id]);
    }

    #[Route('/menus/{id}/archive', name: 'menu_archive', methods: ['POST'])]
    public function archive(int $id, Request $request): Response
    {
        // Vérification CSRF (optionnel)
        $token = $request->request->get('_token');
        
        // Ici vous ajouteriez la logique pour archiver/désarchiver en base
        $this->addFlash('success', 'Menu archivé/désarchivé avec succès!');
        return $this->redirectToRoute('admin_menus');
    }
}
