<?php

namespace App\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
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

    #[Route('/menus/new', name: 'menu_new')]
    public function new(): Response
    {
        return $this->render('admin/menus/new.html.twig');
    }

    #[Route('/menus/{id}/edit', name: 'menu_edit')]
    public function edit(int $id): Response
    {
        return $this->render('admin/menus/edit.html.twig', ['id' => $id]);
    }

    #[Route('/menus/{id}/archive', name: 'menu_archive', methods: ['POST'])]
    public function archive(int $id): Response
    {
        $this->addFlash('success', 'Menu archivé avec succès');
        return $this->redirectToRoute('admin_menus');
    }
}
