<?php

namespace App\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin', name: 'admin_')]
class CommandesController extends AbstractController
{
    #[Route('/commandes', name: 'commandes')]
    public function index(): Response
    {
        return $this->render('admin/commandes/index.html.twig');
    }
}
