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
        return $this->render('admin/menus/index.html.twig');
    }
}
