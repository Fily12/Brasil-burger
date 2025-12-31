<?php

namespace App\Controller\Admin;

use App\Entity\Burger;
use App\Form\BurgerType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/admin/burgers', name: 'admin_burgers_')]
class BurgerController extends AbstractController
{
    #[Route('/', name: 'index')]
    public function index(EntityManagerInterface $em): Response
    {
        $burgers = $em->getRepository(Burger::class)->findAll();

        return $this->render('admin/burgers/list.html.twig', [
            'burgers' => $burgers,
        ]);
    }

    #[Route('/ajouter', name: 'ajouter')]
    public function ajouter(Request $request, EntityManagerInterface $em): Response
    {
        $burger = new Burger();
        $form = $this->createForm(BurgerType::class, $burger);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();
            if ($imageFile) {
                $newFilename = uniqid().'.'.$imageFile->guessExtension();
                $imageFile->move($this->getParameter('burgers_images_directory'), $newFilename);
                $burger->setImageUrl($newFilename);
            }

            $em->persist($burger);
            $em->flush();

            return $this->redirectToRoute('admin_burgers_index');
        }

        return $this->render('admin/burgers/ajouter.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/modifier/{id}', name: 'modifier')]
    public function modifier(int $id, Request $request, EntityManagerInterface $em): Response
    {
        $burger = $em->getRepository(Burger::class)->find($id);
        if (!$burger) {
            throw $this->createNotFoundException("Burger introuvable");
        }

        $form = $this->createForm(BurgerType::class, $burger);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();
            if ($imageFile) {
                $newFilename = uniqid().'.'.$imageFile->guessExtension();
                $imageFile->move($this->getParameter('burgers_images_directory'), $newFilename);
                $burger->setImageUrl($newFilename);
            }

            $em->flush();

            return $this->redirectToRoute('admin_burgers_index');
        }

        return $this->render('admin/burgers/modifier.html.twig', [
            'form' => $form->createView(),
            'burger' => $burger,
        ]);
    }

    #[Route('/archiver/{id}', name: 'archiver')]
    public function archiver(int $id, EntityManagerInterface $em): Response
    {
        $burger = $em->getRepository(Burger::class)->find($id);
        if (!$burger) {
            throw $this->createNotFoundException("Burger introuvable");
        }

        $em->remove($burger);
        $em->flush();

        return $this->redirectToRoute('admin_burgers_index');
    }
}
