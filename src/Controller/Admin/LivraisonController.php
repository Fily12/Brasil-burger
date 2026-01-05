<?php

namespace App\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin', name: 'admin_')]
class LivraisonController extends AbstractController
{
    #[Route('/livraison', name: 'livraison')]
    public function index(): Response
    {
        return $this->render('admin/livraison/index.html.twig');
    }
}
